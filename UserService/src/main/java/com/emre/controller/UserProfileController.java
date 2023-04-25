package com.emre.controller;

import com.emre.dto.request.UserProfileSaveRequestDto;
import com.emre.dto.request.UserProfileUpdateRequestDto;
import com.emre.repository.entity.UserProfile;
import com.emre.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/userprofile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping("getpage")
    public ResponseEntity<String> getPage(){
        return ResponseEntity.ok("UserServiceye ulaştınız");
    }
    @PostMapping("/save")
    public ResponseEntity<Void> save(@RequestBody @Valid UserProfileSaveRequestDto dto){
        userProfileService.save(dto);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/findall")
    public ResponseEntity<List<UserProfile>> findAll(){
        return ResponseEntity.ok(userProfileService.findAll());
    }
    @PostMapping("/update")
    public ResponseEntity<Void> update(@RequestBody @Valid UserProfileUpdateRequestDto dto){
        userProfileService.update(dto);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/updateBean")
    public ResponseEntity<Void> updateBean(@RequestBody @Valid UserProfileUpdateRequestDto dto){
        userProfileService.updateBean(dto);
        return ResponseEntity.ok().build();
    }
    //redis cacheleme mekanizmasını denemek için
    @GetMapping("/getnametoupper")
    public ResponseEntity<String> getNameToUpper(String name){
        return ResponseEntity.ok(userProfileService.getNameToUpper(name));
    }
    //önbelleğe alınan sorgu temizleme metodu.
    @GetMapping("/clearcache")
    public ResponseEntity<Void> clearCache(){
        userProfileService.clearCacheToUpper();
        return ResponseEntity.ok().build();
    }

}
