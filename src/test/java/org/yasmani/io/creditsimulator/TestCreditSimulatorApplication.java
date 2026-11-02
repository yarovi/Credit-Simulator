package org.yasmani.io.creditsimulator;

import org.springframework.boot.SpringApplication;

public class TestCreditSimulatorApplication {

  public static void main(String[] args) {
    SpringApplication.from(CreditSimulatorApplication::main).with(TestcontainersConfiguration.class).run(args);
  }

}
