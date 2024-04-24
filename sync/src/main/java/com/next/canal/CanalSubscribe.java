package com.next.canal;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.common.utils.AddressUtils;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.next.service.TrainNumberService;
import com.next.service.TrainSeatService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.util.List;

@Service
@Slf4j
public class CanalSubscribe implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private TrainSeatService trainSeatService;
    @Resource
    private TrainNumberService trainNumberService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        canalSubscribe();
    }

    private void canalSubscribe() {
        // 创建链接
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress(AddressUtils.getHostIp(),
                11111), "train", "", "");
        int batchSize = 1000;
        new Thread(() -> {
            try {
                log.info("canal subscribe");
                connector.connect();
                connector.subscribe(".*\\..*");
                connector.rollback();
                while (true) {
                    Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
                    long batchId = message.getId();
                    int size = message.getEntries().size();
                    if (batchId == -1 || size == 0) {
                        safeSleep(100);
                        continue;
                    }
                    try {
                        log.info("new message, batchId:{}, size:{}", batchId, size);
                        handleEntry(message.getEntries());
                        connector.ack(batchId); // 提交确认
                    } catch (Exception e1) {
                        log.error("canal data handle exception, batchId:{}", batchId, e1);
                        connector.rollback(batchId); // 处理失败, 回滚数据
                    }
                }
            } catch (Exception e3){
                log.error("canal subscribe exception", e3);
                safeSleep(1000);
                canalSubscribe();
            }
        }).start();
    }

    private void handleEntry(List<CanalEntry.Entry> entrys) throws Exception {
        for (CanalEntry.Entry entry : entrys) {
            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                continue;
            }

            CanalEntry.RowChange rowChange = null;
            try {
                rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("rowChange.parse exception , data:" + entry.toString(), e);
            }

            CanalEntry.EventType eventType = rowChange.getEventType();
            String schemaName = entry.getHeader().getSchemaName();
            String tableName = entry.getHeader().getTableName();
            log.info("name:[{},{}],eventType:{}", schemaName, tableName, eventType);

            for (CanalEntry.RowData rowData : rowChange.getRowDatasList()) {
                if (eventType == CanalEntry.EventType.DELETE) {
                    handleColumn(rowData.getBeforeColumnsList(), eventType, schemaName, tableName);
                } else {
                    handleColumn(rowData.getAfterColumnsList(), eventType, schemaName, tableName);
                }
            }
        }
    }

    private void handleColumn(List<CanalEntry.Column> columns, CanalEntry.EventType eventType, String schemaName, String tableName) throws Exception {
        if (schemaName.contains("train_seat")) { // 处理座位变更
            trainSeatService.handle(columns, eventType);
        } else if (tableName.equals("train_number")) { // 处理车次信息变更
            trainNumberService.handle(columns, eventType);
        } else {
            log.info("drop data, no need care");
        }
    }

    private void safeSleep(int mills) {
        try {
            Thread.sleep(mills);
        } catch (Exception e2) {

        }
    }

}
