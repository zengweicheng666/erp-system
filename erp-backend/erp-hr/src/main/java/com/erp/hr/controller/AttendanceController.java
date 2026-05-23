package com.erp.hr.controller;
import com.erp.common.result.Result;
import com.erp.hr.entity.Attendance;
import com.erp.hr.service.AttendanceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@Tag(name = "考勤管理") @RestController @RequestMapping("/hr/attendance") @RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceService attendanceService;
    @GetMapping("/list") public Result<List<Attendance>> list() { return Result.success(attendanceService.list()); }
    @PostMapping @PreAuthorize("hasAuthority('hr:attendance:add')") public Result<Void> add(@RequestBody Attendance a) { attendanceService.save(a); return Result.success(); }
}
