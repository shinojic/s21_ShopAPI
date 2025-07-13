package ru.shop.domain.service.client;

import org.springframework.stereotype.Service;
import ru.shop.domain.model.AddressDto;
import ru.shop.domain.model.ClientDto;
import ru.shop.domain.service.address.AddressService;

import java.util.List;
import java.util.UUID;

@Service
public interface ClientService {

    /**
     * Добавление клиента.
     *
     * @param client Клиент.
     *               Если данные некорректны, операция не выполняется.
     * @return Успешность выполнения операции.
     */
    boolean add(final ClientDto client);

    /**
     * Удаление клиента
     *
     * @param id  Уникальный идентификатор клиента.
     *            Если не найден в базе, операция не выполняется.
     * @return Успешность выполнения операции.
     */
    boolean deleteById(final UUID id);

    /**
     * Получение клиента по id.
     *
     * @param id Уникальный идентификатор клиента.
     * @return Клиент или null, если не найден.
     */
    ClientDto getById(final UUID id);

    /**
     * Получение клиентов по имени и фамилии.
     *
     * @param name      Имя клиента.
     * @param surname   Фамилия клиента.
     * @return  Список клиентов с заданными именем и фамилией.
     *          Если в репозитории таких нет, возвращается пустой список.
     */
    List<ClientDto> getByNameAndSurname(final String name, final String surname);

    /**
     * Получение списка клиентов с опциональной пагинацией.
     *
     * @param limit  Опциональный параметр, определяющий максимальное количество возвращаемых записей.
     *               Если равен null, возвращаются все записи.
     * @param offset Опциональный параметр, определяющий смещение (начальную позицию) для выборки.
     *               Если равен null, выборка начинается с начала списка.
     * @return Список клиентов. В случае отсутствия параметров возвращается весь список.
     * @throws IllegalArgumentException Если limit < 1 || offset < 1.
     */
    List<ClientDto> getAll(final Integer limit, final Integer offset) throws IllegalArgumentException;

    /**
     * Изменение адреса клиента.
     *
     * @param addressService    Сервис адресов.
     * @param clientId          Уникальный идентификатор клиента.
     *                          Если не найден в базе, операция не выполняется.
     * @param newAddress        Новый адрес.
     *                          Если данные некорректны, операция не выполняется.
     * @return Успешность выполнения операции.
     */
    boolean changeAddress(final AddressService addressService,
                          final UUID clientId, final AddressDto newAddress);

}
