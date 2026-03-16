package com.codewitheyob.store;

import com.codewitheyob.store.entities.Address;
import com.codewitheyob.store.entities.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringStoreApplication {

    public static void main(String[] args) {

       ApplicationContext context =  SpringApplication.run(SpringStoreApplication.class, args);

        var user = User.builder()
                .name("eyob")
                .email("eyob@m.com")
                .password("myPassword!")
                .build();

        var address = Address.builder()
                .state("state")
                .city("city")
                .zip("zip")
                .street("street")
                .build();

        user.setAddresses(address);
        System.out.println(user);

//       context.getBean(NotificationManager.class).sendNotification("Hello, this is a test message!");
//
//       var pay = context.getBean(PaymentManager.class);
//       pay.processPayment(250.0);
//
//       var data = context.getBean(DataManager.class);
//       data.saveData("User information");
//
//       var report = context.getBean(ReportManager.class);
//       report.createReport("Sales data");
//
//        System.out.println("=================");
//        var daily = context.getBean(DailyReportManager.class);
//        var month = context.getBean(MonthlyReportManager.class);
//
//        month.createReport("Monthly sales");
//        daily.createReport("Daily sales");
    }

}
