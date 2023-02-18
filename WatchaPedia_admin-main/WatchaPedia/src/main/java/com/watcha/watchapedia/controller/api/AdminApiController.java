package com.watcha.watchapedia.controller.api;

import com.watcha.watchapedia.controller.CrudController;
import com.watcha.watchapedia.model.entity.AdminUser;
import com.watcha.watchapedia.model.network.Header;
import com.watcha.watchapedia.model.network.request.AdminApiRequest;
import com.watcha.watchapedia.model.network.request.AdminJustIdxRequest;
import com.watcha.watchapedia.model.network.response.AdminApiResponse;
import com.watcha.watchapedia.service.AdminApiLogicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin")    // http://localhost:9090/api/admin
@RequiredArgsConstructor
public class AdminApiController extends CrudController<AdminApiRequest, AdminApiResponse, AdminUser> {
    private final AdminApiLogicService adminApiLogicService;

    @PostMapping("/idCheck")    // http://localhost:9090/api/admin/idCheck
    public Header<AdminApiResponse> idCheck(@RequestBody Header<AdminApiRequest> request) {
        Header<AdminApiResponse> result = adminApiLogicService.idCheck(request);
        System.out.println(result);
        return adminApiLogicService.idCheck(request);
    }

    @PostMapping("/employeeCheck")  // http://localhost:9090/api/admin/employeeCheck
    public Header<AdminApiResponse> employeeCheck(@RequestBody Header<AdminApiRequest> request) {
        return adminApiLogicService.employeeCheck(request);
    }

    @Override
    @PostMapping("/regist")    // http://localhost:9090/api/admin/regist
    public Header<AdminApiResponse> create(@RequestBody Header<AdminApiRequest> request) {
        return adminApiLogicService.create(request);
    }

    @Override
    public Header<AdminApiResponse> read(Long id) {
        return super.read(id);
    }

    @Override
    @PutMapping("")
    public Header<AdminApiResponse> update(@RequestBody Header<AdminApiRequest> request) {
        return adminApiLogicService.update(request);
    }

    @PostMapping("/delete")
    public Header delete(@RequestBody Header<AdminJustIdxRequest> request) {
        Long idx = Long.valueOf(request.getData().getAdminIdx());
        return adminApiLogicService.delete(idx);


    }

    @Override
    public Header<AdminApiResponse> delete(Long id) {
        return super.delete(id);
    }

    @PostMapping("/updateaccount")
    public Header<AdminApiResponse> updateAccount(@RequestBody Header<AdminApiRequest> request) {
        return adminApiLogicService.updateAdmins(request);
    }
}