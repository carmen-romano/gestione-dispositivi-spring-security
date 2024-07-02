package carmenromano.gestione_dispositivi.controllers;

import carmenromano.gestione_dispositivi.entities.Employee;
import carmenromano.gestione_dispositivi.exceptions.BadRequestException;
import carmenromano.gestione_dispositivi.payloads.EmployeePayload;
import carmenromano.gestione_dispositivi.payloads.EmployeePayloadResponse;
import carmenromano.gestione_dispositivi.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public Page<Employee> getAllEmployees(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5")
    int size, @RequestParam(defaultValue = "id") String sortBy) {
        return this.employeeService.getAllEmployees(page, size, sortBy);
    }

    @GetMapping("/{employeeId}")
    public Employee findById(@PathVariable int employeeId) {
        return this.employeeService.findById(employeeId);
    }


    @PutMapping("/{employeeId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Employee findAndUpdate(@PathVariable int employeeId, @RequestBody Employee body) {
        return this.employeeService.findByIdAndUpdate(employeeId, body);
    }


    @DeleteMapping("/{employeeId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable int employeeId) {
        this.employeeService.findByIdAndDelete(employeeId);
    }

    @PostMapping("/{employeeId}/avatar")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Employee uploadAvatar(@RequestParam("avatar") MultipartFile image, @PathVariable int employeeId) throws IOException {
        return this.employeeService.uploadAvatar(employeeId, image);
    }



    // /me

    @PostMapping("/me/avatar")
    public Employee uploadAvatarMe(@RequestParam("avatar") MultipartFile image, @AuthenticationPrincipal Employee currentAuthenticatedUser) throws IOException {
        return this.employeeService.uploadAvatar(currentAuthenticatedUser.getId(), image);
    }


    @GetMapping("/me")
    public Employee getProfile(@AuthenticationPrincipal Employee currentAuthenticatedUser){
        return currentAuthenticatedUser;
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal Employee currentAuthenticatedUser){
        this.employeeService.findByIdAndDelete(Math.toIntExact(currentAuthenticatedUser.getId()));
    }
    @PutMapping("/me")
    public Employee findAndUpdate(@AuthenticationPrincipal Employee currentAuthenticatedUser, @RequestBody Employee body) {
        return this.employeeService.findByIdAndUpdate(currentAuthenticatedUser.getId(), body);
    }

}
