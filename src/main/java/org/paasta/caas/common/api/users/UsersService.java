package org.paasta.caas.common.api.users;

import org.paasta.caas.common.api.common.CommonService;
import org.paasta.caas.common.api.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User Service 클래스
 *
 * @author REX
 * @version 1.0
 * @since 2018.08.02
 */
@Service
public class UsersService {

    private final CommonService commonService;
    private final UsersRepository userRepository;

    /**
     * Instantiates a new User service.
     *
     * @param commonService  the common service
     * @param userRepository the user repository
     */
    @Autowired
    public UsersService(CommonService commonService, UsersRepository userRepository) {
        this.commonService = commonService;
        this.userRepository = userRepository;}

    /**
     * Gets user list.
     *
     * @return the user list
     */
    List<Users> getUserList() {
        return userRepository.findAll();
    }

    /**
     * Gets user.
     *
     * @param id the id
     * @return the user
     */
    Users getUser(int id) {
        return userRepository.getOne((long) id);
    }

    /**
     * Gets user.
     *
     * @param serviceInstanceId the serviceInstanceId
     * @param organizationGuid the organizationGuid
     * @return the user
     */
    List<Users> getUsersByServiceInstanceIdAndOrganizationGuid(String serviceInstanceId, String organizationGuid) {
        return userRepository.findByServiceInstanceIdAndOrganizationGuid(serviceInstanceId, organizationGuid);
    }

    /**
     * Create user user.
     *
     * @param user the user
     * @return the user
     */
    Users createUser(Users user) {
        String result = commonService.procValidator(user);

        if (result.equals(Constants.RESULT_STATUS_SUCCESS)) {
            return userRepository.save(user);
        } else {
            return (Users) commonService.setResultModel(Users.class, Constants.RESULT_STATUS_FAIL, result);
        }
    }

    /**
     * Update user user.
     *
     * @param user the user
     * @return the user
     */
    Users updateUser(Users user) {
        String result = commonService.procValidator(user);

        if (result.equals(Constants.RESULT_STATUS_SUCCESS)) {
            return userRepository.save(user);
        } else {
            return (Users) commonService.setResultModel(Users.class, Constants.RESULT_STATUS_FAIL, result);
        }
    }

    /**
     * Delete user string.
     *
     * @param user the user
     * @return the string
     */
    Users deleteUser(Users user) {
        userRepository.delete(user);
        return (Users) commonService.setResultModel(Users.class, Constants.RESULT_STATUS_SUCCESS, "");
    }
}