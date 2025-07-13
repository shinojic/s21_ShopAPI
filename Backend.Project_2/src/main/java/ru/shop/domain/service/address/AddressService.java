package ru.shop.domain.service.address;

import org.springframework.stereotype.Service;
import ru.shop.domain.model.AddressDto;

import java.util.UUID;

@Service
public interface AddressService {

    /**
     * Добавление нового адреса в базу данных.
     *
     * @param address Адрес.
     *                Если равен null или данные невалидны, операция не выполняется.
     * @return Успешность выполнения операции.
     */
    boolean add(final AddressDto address);

    /**
     * Удаление адреса из репозитория по id.
     *
     * @param id Уникальный идентификатор.
     *           Если равен null или отсутствует в репозитории, операция не выполняется.
     * @return Успешность выполнения операции.
     */
    boolean deleteById(final UUID id);

}
