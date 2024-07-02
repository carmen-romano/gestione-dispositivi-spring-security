package carmenromano.gestione_dispositivi.controllers;

import carmenromano.gestione_dispositivi.entities.Device;
import carmenromano.gestione_dispositivi.entities.Employee;
import carmenromano.gestione_dispositivi.exceptions.BadRequestException;
import carmenromano.gestione_dispositivi.payloads.DeviceAssignmentPayload;
import carmenromano.gestione_dispositivi.payloads.DevicePayload;
import carmenromano.gestione_dispositivi.services.DeviceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/devices")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @GetMapping
    public Page<Device> getAllEmployees(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5")
    int size, @RequestParam(defaultValue = "id") String sortBy) {
        return this.deviceService.getAllDevice(page, size, sortBy);
    }

    @GetMapping("/{deviceId}")
    public Device findById(@PathVariable int deviceId) {
        return deviceService.findById(deviceId);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Device saveDevice(@RequestBody @Valid DevicePayload body, BindingResult validation) throws IOException {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return deviceService.save(body);
    }

    @PutMapping("/{deviceId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Device findByIdAndUpdate(@PathVariable int deviceId, @RequestBody Device body) {
        return deviceService.findByIdAndUpdate(deviceId, body);
    }

    @DeleteMapping("/{deviceId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable int deviceId) {
        deviceService.findByIdAndDelete(deviceId);
    }

    @PostMapping("/assign/{deviceId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Device assignDeviceToEmployee(
            @PathVariable int deviceId,
            @RequestBody @Valid DeviceAssignmentPayload body,
            BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return deviceService.assignDeviceToEmployee(deviceId, body);
    }
}
