package ru.seals.auth.util;

import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;
import ru.seals.auth.dto.UserDTO;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class Convertor {
    private static final String keycloakPhoneName = "phoneNumber";

    /*private List<UserDTO> mapUsers(List<UserRepresentation> userRepresentations) {
        List<UserDTO> users = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(userRepresentations)) {
            userRepresentations.forEach(userRep -> {
                users.add(mapUser(userRep));
            });
        }
        return users;
    }*/

    public static <R, E> List<R> convertList(List<E> list, Function<E, R> converter) {
        return list.stream().map(converter).collect(Collectors.toList());
    }

    public UserDTO mapUser(UserRepresentation userRep) {
        UserDTO user = new UserDTO();
        user.setEmail(userRep.getEmail());
        user.setFirstName(userRep.getFirstName());
        user.setLastName(userRep.getLastName());
        user.setPhone(userRep.getAttributes().get(keycloakPhoneName).get(0));

        return user;
    }
}
