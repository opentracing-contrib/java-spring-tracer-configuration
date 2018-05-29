/**
 * Copyright 2018 The OpenTracing Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package io.opentracing.spring.tracer.configuration;

import static org.junit.Assert.assertTrue;

import io.opentracing.Tracer;
import io.opentracing.mock.MockSpan;
import io.opentracing.mock.MockTracer;
import io.opentracing.util.GlobalTracer;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(
        classes = {TracerAutoConfigurationWithWrapperAndRegisteredTracerTest.SpringConfiguration.class,
                TestTracerBeanPostProcessor.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class TracerAutoConfigurationWithWrapperAndRegisteredTracerTest extends BaseTest {

  @BeforeClass
  public static void setGlobalTracer() {
    // Pre-register a tracer with the GlobalTracer
    GlobalTracer.register(new MockTracer());
  }

  @Autowired
  private Tracer tracer;

  @Configuration
  @EnableAutoConfiguration
  public static class SpringConfiguration {
  }

  @Test
  public void testGetAutoWiredTracer() {
    assertTrue(tracer instanceof TestTracerBeanPostProcessor.TracerWrapper);
    assertTrue(GlobalTracer.isRegistered());
    assertTrue(tracer.buildSpan("hello").start() instanceof MockSpan);
  }

}
