/*
 * Copyright 2017, OpenSkywalking Organization All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Project repository: https://github.com/OpenSkywalking/skywalking
 */

package org.skywalking.apm.collector.boot;

import org.skywalking.apm.collector.boot.config.ApplicationConfigLoader;
import org.skywalking.apm.collector.boot.config.ConfigFileNotFoundException;
import org.skywalking.apm.collector.core.module.ApplicationConfiguration;
import org.skywalking.apm.collector.core.module.ModuleManager;
import org.skywalking.apm.collector.core.module.ModuleNotFoundException;
import org.skywalking.apm.collector.core.module.ProviderNotFoundException;
import org.skywalking.apm.collector.core.module.ServiceNotProvidedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Collector 启动入口
 *
 * @author peng-yongsheng
 */
public class CollectorBootStartUp {

    public static void main(String[] args) {
        final Logger logger = LoggerFactory.getLogger(CollectorBootStartUp.class);

        ApplicationConfigLoader configLoader = new ApplicationConfigLoader();
        ModuleManager manager = new ModuleManager();
        try {
            // 加载 配置
            ApplicationConfiguration applicationConfiguration = configLoader.load();
            // 初始化 组件
            manager.init(applicationConfiguration);
        } catch (ConfigFileNotFoundException e) {
            logger.error(e.getMessage(), e);
        } catch (ModuleNotFoundException e) {
            logger.error(e.getMessage(), e);
        } catch (ProviderNotFoundException e) {
            logger.error(e.getMessage(), e);
        } catch (ServiceNotProvidedException e) {
            logger.error(e.getMessage(), e);
        }

        // sleep ，等待 Jetty Server 启动完成
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
        }
    }
}
