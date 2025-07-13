package ru.shop.domain.service.supplier;

import org.springframework.stereotype.Service;
import ru.shop.domain.model.AddressDto;
import ru.shop.domain.model.SupplierDto;
import ru.shop.domain.service.address.AddressService;

import java.util.List;
import java.util.UUID;

@Service
public interface SupplierService {

    /**
     * Добавление поставщика.
     *
     * @param supplier Объект поставщика.
     *                 Если данные некорректны, операция не выполняется.
     * @return Успешность выполнения операции.
     */
    boolean add(final SupplierDto supplier);

    /**
     * Изменение адреса поставщика.
     *
     * @param addressService    Сервис адресов.
     * @param supplierId        Уникальный идентификатор поставщика.
     *                          Если не найден, операция не выполняется.
     * @param newAddress        Объект нового адреса.
     *                          Если данные некорректны, операция не выполняется.
     *                          Старый адрес удаляется из базы данных адресов, а новый - добавляется.
     * @return Успешность выполнения операции.
     */
    boolean changeAddress(final AddressService addressService,
                          final UUID supplierId, final AddressDto newAddress);

    /**
     * Удаление поставщика по уникальному идентификатору.
     *
     * @param id Уникальный идентификатор поставщика.
     *           Если не найден, операция не выполняется.
     * @return Успешность выполнения операции.
     */
    boolean deleteById(final UUID id);

    /**
     * Получение списка всех поставщиков.
     *
     * @return Список всех поставщиков. Если поставщиков нет, отправляется пустой список.
     */
    List<SupplierDto> getAll();

    /**
     * Получение поставщика по уникальному идентификатору.
     *
     * @param id Уникальный идентификатор поставщика.
     *           Если не найден, возвращается null.
     * @return Объект поставщика или null.
     */
    SupplierDto getById(final UUID id);

}
