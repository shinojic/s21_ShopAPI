package ru.shop.domain.service.product;

import org.springframework.stereotype.Service;
import ru.shop.domain.model.ProductDto;

import java.util.List;
import java.util.UUID;

@Service
public interface ProductService {

    /**
     * Добавление нового товара.
     *
     * @param product Объект товара.
     *                Если данные товара некорректны, операция не выполняется.
     * @return Успешность выполнения операции.
     */
    boolean add(final ProductDto product);

    /**
     * Уменьшение количества единиц товара на складе.
     *
     * @param id        Уникальный идентификатор товара.
     * @param amount    Количество единиц, на которое нужно уменьшить количество доступного товара.
     * @return Успешность выполнения операции.
     * @throws IllegalArgumentException Некорректное количество единиц
     */
    boolean reduceByAmount(final UUID id, final Integer amount) throws IllegalArgumentException;

    /**
     * Получение товара по уникальному идентификатору.
     *
     * @param id Уникальный идентификатор.
     * @return Объект товара или null, если не найден.
     */
    ProductDto getById(final UUID id);

    /**
     * Получение списка всех товаров.
     *
     * @return Список товаров. Пустой список, если товары отсутствуют.
     */
    List<ProductDto> getAll();

    /**
     * Удаление товара по уникальному идентификатору.
     *
     * @param id Уникальный идентификатор.
     * @return Успешность выполнения операции.
     */
    boolean deleteById(final UUID id);

}
