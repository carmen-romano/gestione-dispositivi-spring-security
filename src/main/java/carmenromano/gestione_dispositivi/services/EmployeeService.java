package carmenromano.gestione_dispositivi.services;

import carmenromano.gestione_dispositivi.entities.Employee;
import carmenromano.gestione_dispositivi.enums.RoleType;
import carmenromano.gestione_dispositivi.exceptions.BadRequestException;
import carmenromano.gestione_dispositivi.exceptions.NotFoundException;
import carmenromano.gestione_dispositivi.payloads.EmployeePayload;
import carmenromano.gestione_dispositivi.repositories.EmployeeRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class EmployeeService {
    @Autowired
    private Cloudinary cloudinaryUploader;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    public Employee save(EmployeePayload body) throws IOException {
        employeeRepository.findByEmail(body.email()).ifPresent(user -> {
            throw new BadRequestException("L'email " + body.email() + " è già stata utilizzata");
        });
        Employee employee = new Employee();
        employee.setAvatar("https://ui-avatars.com/api/?name=" + body.firstName() + "+" + body.lastName());
        employee.setFirstname(body.firstName());
        employee.setLastname(body.lastName());
        employee.setUsername(body.username());
        employee.setEmail(body.email());
        employee.setRoleType(RoleType.USER);
        employee.setPassword(passwordEncoder.encode(body.password()));

        return employeeRepository.save(employee);
    }

    public Page<Employee> getAllEmployees(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return employeeRepository.findAll(pageable);
    }

    public Employee findById(int id) {
        return employeeRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(int id) {
        Employee found = this.findById(id);
        employeeRepository.delete(found);
    }


    public Employee findByIdAndUpdate(int id, Employee body) {

        Employee found = this.findById(id);
        found.setFirstname(body.getFirstname());
        found.setLastname(body.getLastname());
        found.setUsername(body.getUsername());
        found.setEmail(body.getEmail());
        found.setPassword(body.getPassword());
        return employeeRepository.save(found);
    }

    public Employee uploadAvatar(int id, MultipartFile file) throws IOException {
        Employee found = this.findById(id);
        String avatarURL = (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setAvatar(avatarURL);
        return employeeRepository.save(found);
    }
    public Employee findByEmail(String email){
        return employeeRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Employee with email " + email + " not found!"));
    }
}
