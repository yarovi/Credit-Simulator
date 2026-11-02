package org.yasmani.io.creditsimulator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class CreditSimulatorApplicationTests {

  @Test
  void contextLoads() {
  }

}
