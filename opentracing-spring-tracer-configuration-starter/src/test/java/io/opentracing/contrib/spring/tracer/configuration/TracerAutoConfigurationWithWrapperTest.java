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
package io.opentracing.contrib.spring.tracer.configuration;

import static org.junit.Assert.assertTrue;

import io.opentracing.Tracer;
import io.opentracing.noop.NoopSpan;
import io.opentracing.util.GlobalTracer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(
        classes = {
                BaseTest.SpringConfiguration.class,
                TestTracerBeanPostProcessor.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class TracerAutoConfigurationWithWrapperTest extends BaseTest {

  @Autowired
  private Tracer tracer;

  @Test
  public void testGetAutoWiredTracer() {
    assertTrue(tracer instanceof TestTracerBeanPostProcessor.TracerWrapper);
    // No tracer has actually been provided, but there is a wrapper created
    // in a BeanPostProcessor, so this wrapper around the NoopTracer gets
    // registered with the GlobalTracer.
    assertTrue(GlobalTracer.isRegistered());
    assertTrue(tracer.buildSpan("hello").start() instanceof NoopSpan);
  }

}
