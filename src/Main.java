
import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.exceptions.CustomerNotExistException;
import org.example.exceptions.ProductNotExistException;
import org.example.exceptions.QuantityException;

import javax.management.ObjectName;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@Data
public class Main {
    public static void main(String[] args)
            throws CustomerNotExistException, ProductNotExistException, QuantityException {
        System.out.println("Магазин Онлаин");

        Service.getCustomerList().add(new Customer("Иванов Иван",
                LocalDate.of(1990, 1, 1),
                "7018887766", Gender.male));
        Service.getCustomerList().add(new Customer("Иванова Дарья",
                LocalDate.of(1991, 8, 5),
                "7058887755", Gender.female));
        Service.getCustomerList().add(new Customer("Сергеев Иван",
                LocalDate.of(1992, 2, 6),
                "7028887744", Gender.male));
        Service.getCustomerList().add(new Customer("Петрова Анастасия",
                LocalDate.of(1989, 5, 7),
                "7088887733", Gender.female));
        Service.getCustomerList().add(new Customer("Иванов Петр",
                LocalDate.of(1988, 6, 12),
                "7068887722", Gender.male));
        Service.getCustomerList().add(new Customer("Сидоров Иван",
                LocalDate.of(1995, 3, 13),
                "7008887711", Gender.male));
        Service.getCustomerList().add(new Customer("No Name",
                LocalDate.of(2000, 12, 31),
                "7778887711", Gender.notSelected));

        Service.getProductList().add(new Product("Яблоки", BigDecimal.valueOf(10L)));
        Service.getProductList().add(new Product("Бананы", BigDecimal.valueOf(20L)));
        Service.getProductList().add(new Product("Апельсин", BigDecimal.valueOf(15L)));
        Service.getProductList().add(new Product("Киви", BigDecimal.valueOf(30L)));
        Service.getProductList().add(new Product("Лук", BigDecimal.valueOf(3L)));

//        Service.getOrderList().add(new Order(customers.get(0), products.get(0), 5));
//        Service.getOrderList().add(new Order(customers.get(1), products.get(3), 10));
//        Service.getOrderList().add(new Order(customers.get(2), products.get(1), 7));
//        Service.getOrderList().add(new Order(customers.get(3), products.get(2), 2));
        try {
            Order order = Service.makePurchase("Иванов Петр", "Апельсин", "5");
            Service.getOrderList().add(order);
            System.out.println(Service.getOrderList());

            Order order2 = Service.makePurchase("Иванова Дарья", "Яблоки", "2");
            Service.getOrderList().add(order2);
            System.out.println(Service.getOrderList());

            Order order3 = Service.makePurchase("Сергеев Иван", "Лук", "30");
            Service.getOrderList().add(order3);
            System.out.println(Service.getOrderList());

            Order order4 = Service.makePurchase("Петрова Анастасия", "Киви", "5");
            Service.getOrderList().add(order4);
            System.out.println(Service.getOrderList());
//            order = Service.makePurchase("John Dow", "Яблоки", "10");
//            System.out.println(order);
//            order = Service.makePurchase("Сидоров Иван", "Ананас", "4");
//            System.out.println(order);
//            order = Service.makePurchase("Сидоров Иван", "Киви", "-1");
//            System.out.println(order);
        } catch (QuantityException ex) {
            Service.getOrderList()
                    .add(Service.makePurchase(ex.getCustomer(), ex.getProduct(), "1"));
        } catch (ProductNotExistException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            throw ex;
        }

        System.out.println(Service.getOrderList());
        System.out.println(Service.getOrderList().size() + " полученные заказы ");

        System.out.println(congratulations(Service.getCustomerList()));
    }

    public static String congratulations(List<Customer> customers) {
        LocalDate testDate = LocalDate.of(2024, 8, 23);
        List<Customer> customersToCongratulateBirthdate = new ArrayList<>();
        List<Customer> customersToCongratulateNewYear = new ArrayList<>();
        List<Customer> customersToCongratulateDefendersFatherland = new ArrayList<>();
        List<Customer> customersToCongratulateInternationalWomensDay = new ArrayList<>();

        for (Customer customer : customers) {
            if (customer.getDateOfBirth().getMonthValue() == testDate.getMonthValue()
                    && customer.getDateOfBirth().getDayOfMonth() == testDate.getDayOfMonth()) {
                customersToCongratulateBirthdate.add(customer);
            }
            if (testDate.getMonthValue() == Holidays.NEW_YEAR.getMonth()
                    && testDate.getDayOfMonth() == Holidays.NEW_YEAR.getDay()) {
                customersToCongratulateNewYear.add(customer);
            }
            if (testDate.getMonthValue() == Holidays.DEFENDER_FATHERLAND_DAY.getMonth()
                    && testDate.getDayOfMonth() == Holidays.DEFENDER_FATHERLAND_DAY.getDay()
                    && customer.getGender() == Gender.male) {
                customersToCongratulateDefendersFatherland.add(customer);
            }
            if (testDate.getMonthValue() == Holidays.INTERNATIONAL_WOMENS_DAY.getMonth()
                    && testDate.getDayOfMonth() == Holidays.INTERNATIONAL_WOMENS_DAY.getDay()
                    && customer.getGender() == Gender.female) {
                customersToCongratulateInternationalWomensDay.add(customer);
            }

        }
        if (!customersToCongratulateBirthdate.isEmpty()) {
            coungratulateToHoliday(customersToCongratulateBirthdate, " с днем рождения!");
        }

        if (!customersToCongratulateNewYear.isEmpty()) {
            coungratulateToHoliday(customersToCongratulateNewYear, " с наступающим Новым годом!");
        } else if (!customersToCongratulateDefendersFatherland.isEmpty()) {
            coungratulateToHoliday(customersToCongratulateDefendersFatherland, " с Днем защитника Отечества!");
        } else if (!customersToCongratulateInternationalWomensDay.isEmpty()) {
            coungratulateToHoliday(customersToCongratulateInternationalWomensDay, " с Международным женским днем!");
        } else {
            return "Сегодня рабочий (не праздничный день)";
        }
        return "";
    }

    public static void coungratulateToHoliday(List<Customer> customers, String message) {
        for (Customer customer : customers) {
            System.out.println(STR."Поздравляем \{customer.getName()}\{message}");
        }
    }
}