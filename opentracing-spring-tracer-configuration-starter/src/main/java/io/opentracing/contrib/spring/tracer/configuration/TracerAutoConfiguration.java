/**
 * Copyright 2018-2019 The OpenTracing Authors
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

import io.opentracing.Tracer;
import io.opentracing.contrib.tracerresolver.TracerResolver;
import io.opentracing.noop.NoopTracerFactory;
import io.opentracing.util.GlobalTracer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Pavol Loffay
 */
@Configuration
public class TracerAutoConfiguration {
  private static final Log log = LogFactory.getLog(TracerAutoConfiguration.class.getName());

  /**
   * This method provides tracer if user did not specify any tracer bean.
   * <p>
   * The order of getting the tracer is:
   * <ol>
   *     <li>Tracer registered in {@link GlobalTracer#registerIfAbsent(Tracer)}</li>
   *     <li>Tracer resolved from {@link TracerResolver#resolveTracer()}</li>
   *     <li>Default tracer, which is {@link io.opentracing.noop.NoopTracer}</li>
   * </ol>
   * @return tracer
   */
  @Bean
  @ConditionalOnMissingBean(Tracer.class)
  public Tracer getTracer() {
    Tracer tracer;
    if (GlobalTracer.isRegistered()) {
      log.warn("GlobalTracer is already registered. For consistency it is best practice to provide " +
              "a Tracer bean instead of manually registering it with the GlobalTracer");
      tracer = GlobalTracer.get();
    } else {
      tracer = TracerResolver.resolveTracer();
      if (tracer == null) {
        // WARNING: Don't return GlobalTracer.get() as this will result in a
        // stack overflow if the returned tracer is subsequently wrapped by a
        // BeanPostProcessor. The post processed tracer would then be registered
        // with the {@link GlobalTracer) (via the {@link TracerRegisterAutoConfiguration})
        // resulting in the wrapper both wrapping the GlobalTracer, as well as being
        // the tracer used by the GlobalTracer.
        tracer = NoopTracerFactory.create();
      }
    }
    log.warn("Tracer bean is not configured! Switching to " + tracer);
    return tracer;
  }
}
