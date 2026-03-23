package com.codewitheyob.store;

import com.codewitheyob.store.services.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringStoreApplication {

    public static void main(String[] args) {

       ApplicationContext context =  SpringApplication.run(SpringStoreApplication.class, args);

        var user = context.getBean(UserService.class);
        user.getAddressById();
//        var address = Address.builder()
//                .state("state")
//                .city("city")
//                .zip("zip")
//                .street("street")
//                .build();

//        user.setAddresses(address);

//        var profile = Profile.builder()
//                        .bio("bio")
//                                .build();
//        user.setProfile(profile);
//        profile.setUser(user);
//
//        System.out.println(user);
//
//        System.out.println("======= Category-Product===========");
//
//        var category = Category.builder()
//                .name("name")
//                .build();
//        BigDecimal seven = BigDecimal.valueOf(7);
//        var product = Product.builder()
//                .name("ma,e")
//                .price(seven)
//                .build();
//
//        category.getProducts().add(product);
//        product.setCategory(category);
//
//        System.out.println(category);

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
