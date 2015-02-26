/*
 * Copyright (c) 2015 Allogy Interactive.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.allogy.amazonaws.elasticbeanstalk.worker.simulator.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author David Venable
 */
@SpringBootApplication
public class Application
{
    public static void main(String[] args)
    {
        ApplicationContext applicationContext = SpringApplication.run(Application.class, args);

        BridgeService bridgeService = applicationContext.getBean(BridgeService.class);
        bridgeService.start();
    }
}