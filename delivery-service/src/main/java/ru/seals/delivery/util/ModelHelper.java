package ru.seals.delivery.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.seals.delivery.model.common.BaseEntity;

@Slf4j
@UtilityClass
public class ModelHelper {
    public <T extends BaseEntity> T createObjectWithId(Long id, Class<T> oClass) throws Exception {
        if (id == null) {
            return null;
        } else {
            T o = oClass.getDeclaredConstructor().newInstance();
            o.setId(id);
            return o;
        }
    }
    public <T extends BaseEntity> T createObjectWithIdSafe(Long id, Class<T> oClass) {
        try {
            return createObjectWithId(id, oClass);
        } catch (Exception e) {
            log.warn("Error occurred while executing ModelHelper.createObjectWithIdSafe(Long, Class). id is {}", id);
            return null;
        }
    }
}
