package com.watcha.watchapedia.service;

import com.watcha.watchapedia.model.entity.AdminUser;
import com.watcha.watchapedia.model.entity.Notice;
import com.watcha.watchapedia.model.network.Header;
import com.watcha.watchapedia.model.network.request.AdminApiRequest;
import com.watcha.watchapedia.model.network.request.NoticeApiRequest;
import com.watcha.watchapedia.model.network.response.AdminApiResponse;
import com.watcha.watchapedia.model.network.response.NoticeApiResponse;
import com.watcha.watchapedia.model.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminApiLogicService extends BaseService<AdminApiRequest, AdminApiResponse, AdminUser> {

    private final AdminRepository adminRepository;

    private AdminApiResponse response(AdminUser admin){
        AdminApiResponse adminApiResponse = AdminApiResponse.builder()
                .adminIdx(admin.getAdminIdx())
                .adminId(admin.getAdminId())
                .adminName(admin.getAdminName())
                .adminNumber(admin.getAdminNumber())
                .adminPw(admin.getAdminPw())
                .adminType(admin.getAdminType())
                .build();

        System.out.println(adminApiResponse);

        return adminApiResponse;
    }

    public Header<AdminApiResponse> idCheck(Header<AdminApiRequest> request) {
        String inputId = request.getData().getAdminId();
        System.out.println(inputId);
        return adminRepository.findByAdminId(inputId).map(admin -> response(admin)).map(result -> Header.OK(result)).orElseGet(()->Header.ERROR("사용가능"));

        //admin_idx	    admin_name	    admin_id	admin_pw	admin_number	admin_type  	reg_date	            update_date
        //1	            안승균	        asg5450	    asg1592	    2066001	        hradmin	        2023-01-17 21:21:40	    2023-01-17 21:21:40

    }

    public Header<AdminApiResponse> employeeCheck(Header<AdminApiRequest> request) {
        String inputId = request.getData().getAdminNumber();
        System.out.println(inputId);
        return adminRepository.findByAdminNumber(inputId).map(admin -> response(admin)).map(Header::OK).orElseGet(()->Header.ERROR("사용가능"));
    }

    @Override
    public Header<AdminApiResponse> create(Header<AdminApiRequest> request) {
        AdminApiRequest adminUserApiRequest = request.getData();

        AdminUser users = AdminUser.builder()
                .adminId(adminUserApiRequest.getAdminId())
                .adminPw(adminUserApiRequest.getAdminPw())
                .adminNumber(adminUserApiRequest.getAdminNumber())
                .adminType(adminUserApiRequest.getAdminType())
                .adminName(adminUserApiRequest.getAdminName())
                .build();
        AdminUser newUsers = adminRepository.save(users);
        return Header.OK(response(newUsers));
    }

    @Override
    public Header<AdminApiResponse> read(Long id) {
        return adminRepository.findById(id).map(admin -> response(admin))
                .map(Header::OK).orElseGet(()->Header.ERROR("데이터 없음"));
    }

    public Header<AdminApiResponse> read(String userid, String userpw) {
        return adminRepository.findByAdminIdAndAdminPw(userid, userpw).map(
                        users -> response(users)).map(Header::OK)
                .orElseGet(()->Header.ERROR("아이디 또는 비밀번호가 틀렸음!"));
    }

    @Override
    public Header<AdminApiResponse> update(Header<AdminApiRequest> request){
        System.out.println("adminapilogicservice update 실행");
        AdminApiRequest adminApiRequest = request.getData();
        Optional<AdminUser> admin = adminRepository.findById(adminApiRequest.getAdminIdx());
        System.out.println("request.getData().getOldPw()"+request.getData().getOldPw());
        System.out.println("admin.get().getAdminPw()"+admin.get().getAdminPw());
        if(request.getData().getOldPw().equals(admin.get().getAdminPw())){
            System.out.println("입력한비번이 기존비번과 같음");
            return admin.map(
                            admins -> {
                                admins.setAdminPw(adminApiRequest.getAdminPw());
                                return admins;
                            })
                    .map(ad -> adminRepository.save(ad))
                    .map(adm -> response(adm))
                    .map(Header::OK)
                    .orElseGet(() -> Header.ERROR("데이터 없음")
                    );
        }else{
            System.out.println("입력한비번이 기존비번과 다름");
            return Header.ERROR("데이터 없음");
        }
    }

    @Override
    public Header delete(Long id) {
        Optional<AdminUser> users = adminRepository.findById(id);
        return users.map(user -> {
            adminRepository.delete(user);
            return Header.OK();
        }).orElseGet(() -> Header.ERROR("데이터 없음"));
    }
    public Header<AdminApiResponse> updateAdmins(Header<AdminApiRequest> request){
        System.out.println("AdminRepository update 실행");
        AdminApiRequest adminApiRequest = request.getData();

        Optional<AdminUser> admins = adminRepository.findById(adminApiRequest.getAdminIdx());
        System.out.println("admin Idx :" +adminApiRequest.getAdminIdx());
        System.out.println("원래비번 :" +admins.get().getAdminPw());
        System.out.println("새로운비번 :" +adminApiRequest.getAdminPw());

        return admins.map(
                        admin -> {
                            admin.setAdminPw(adminApiRequest.getAdminPw());
                            admin.setAdminName(adminApiRequest.getAdminName());
                            admin.setAdminType(adminApiRequest.getAdminType());

                            return admin;

                        }).map(admin -> adminRepository.save(admin))
                .map(admin -> response(admin)).map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없읍")
                );

    }

}
