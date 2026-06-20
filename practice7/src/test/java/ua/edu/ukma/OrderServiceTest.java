package ua.edu.ukma;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


// тестовий клас з інтеграцією Mockito та AssertJ.
@ExtendWith(MockitoExtension.class) // Mockito для керування життєвим циклом моків
class OrderServiceTest {

    // створення мок-об'єктів
    @Mock
    private InventoryService inventoryService;

    @Mock
    private PaymentGateway paymentGateway;

    @Mock
    private NotificationService notificationService;

    // створення об'єкта OrderService та впровадження в нього створених вище моків через конструктор
    @InjectMocks
    private OrderService orderService;

    // тест 1: Успішне замовлення + Перевірка void-методів (verify, times)
    @Test
    void shouldPlaceOrderSuccessfully_whenStockAndPaymentAreOk() {

        Order order = new Order("123", "PROD_1", 2, 200.0);

        // поведінка для моків
        when(inventoryService.checkStock(anyString(), anyInt())).thenReturn(true);
        when(paymentGateway.processPayment(200.0)).thenReturn(true);

        // дія
        boolean result = orderService.placeOrder(order);

        // Перевірка результату за допомогою AssertJ
        assertThat(result).isTrue();

        // перевірка метод списання викликано рівно 1 раз
        verify(inventoryService, times(1)).decreaseStock("PROD_1", 2);

        // перевірка сервіс сповіщення надіслав email
        verify(notificationService).sendEmail("123", "Payment successful");
    }

    // тест 2: немає товару на складі + перевірка взаємодії (never)
    // перевіряє сценарій коли товару недостатньо на складі.
    @Test
    void shouldFailOrder_whenOutOfStock() {
        Order order = new Order("124", "PROD_1", 10, 1000.0);

        // конфігуруємо склад так, ніби товару недостатньо
        when(inventoryService.checkStock("PROD_1", 10)).thenReturn(false);

        boolean result = orderService.placeOrder(order);

        assertThat(result).isFalse();

        // перевіряємо захисну логіку: якщо складу немає, шлюз оплати ніколи (never) не викликається
        verify(paymentGateway, never()).processPayment(anyDouble());
        verify(inventoryService, never()).decreaseStock(anyString(), anyInt());
    }

    // тест 3: помилка оплати
    @Test
    void shouldFailOrder_whenPaymentFails() {
        Order order = new Order("125", "PROD_1", 1, 50.0);

        // Товар є, але транзакція відхилена шлюзом
        when(inventoryService.checkStock("PROD_1", 1)).thenReturn(true);
        when(paymentGateway.processPayment(50.0)).thenReturn(false);

        boolean result = orderService.placeOrder(order);

        assertThat(result).isFalse();

        // перевіряємо, що товар НЕ списався зі складу, так як неуспішна оплата
        verify(inventoryService, never()).decreaseStock(anyString(), anyInt());
    }

    // тест 4: Демонстрація SoftAssertions
    @Test
    void orderFieldsShouldBeCorrectlyAssigned() {
        Order order = new Order("999", "SKU1", 5, 250.0);

        // М'які твердження дозволяють виконати ВСІ перевірки, навіть якщо одна з них впаде
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(order.getId()).isEqualTo("999");
        softly.assertThat(order.getProductId()).startsWith("SKU");
        softly.assertThat(order.getQuantity()).isGreaterThan(0);
        softly.assertThat(order.getAmount()).isEqualTo(250.0);
        softly.assertAll(); // Лише в цій точці генерується сумарний звіт про помилки
    }

    // тест 5: AssertJ перевірки колекцій (3 типи перевірок)
    // перевірка списку останніх замовлень
    @Test
    void shouldReturnCorrectRecentOrdersList() {
        List<Order> orders = orderService.getRecentOrders();

        // 1 перевірка розмір списку
        assertThat(orders).hasSize(3);

        // 2 перевірка типу екстракція полів та строгий порядок значень
        assertThat(orders)
                .extracting(Order::getProductId)
                .containsExactly("PROD_A", "PROD_B", "PROD_C");

        // 3 перевірка типу 3: фільтрація, екстракція кількох полів та порівняння об'єктів (без урахування порядку)
        assertThat(orders)
                .filteredOn(o -> o.getAmount() > 60.0)
                .extracting(Order::getId, Order::getProductId)
                .containsExactlyInAnyOrder(
                        tuple("1", "PROD_A"),
                        tuple("3", "PROD_C")
                );
    }


    // мутаційне тестування
    // перевіряє логіку розрахунку знижки з умовою що загальна кількість >= 1000.0).
    @Test
    void calculateDiscount_mutantSurvives() {
        // Перевіряємо лише очевидні значення, ігноруючи межу
        assertThat(orderService.calculateDiscount(1500.0)).isEqualTo(1350.0);
        assertThat(orderService.calculateDiscount(500.0)).isEqualTo(500.0);
        // Якщо PIT змінить ">= 1000" на "> 1000", цей тест все одно світитиметься зеленим,
        // тобто він "впаде" перед мутацією (не помітить її).
    }


}