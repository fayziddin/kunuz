package dasturlash.uz.service;

import dasturlash.uz.dto.auth.AuthResponseDTO;
import dasturlash.uz.dto.auth.AuthorizationDTO;
import dasturlash.uz.dto.auth.RegistrationDTO;
import dasturlash.uz.dto.profile.ProfileDTO;
import dasturlash.uz.entity.ProfileEntity;
import dasturlash.uz.enums.ProfileRoleEnum;
import dasturlash.uz.enums.ProfileStatusEnum;
import dasturlash.uz.exceptions.AppBadException;
import dasturlash.uz.repository.ProfileRepository;
import dasturlash.uz.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ProfileRoleService profileRoleService;

//    @Autowired
//    private EmailSendingService emailSendingService;
//    @Autowired
//    private EmailHistoryService emailHistoryService;

    @Autowired
    private SmsSenderService smsSenderService;
    @Autowired
    private SmsHistoryService smsHistoryService;


    public String registration(RegistrationDTO dto) {
        // check
        Optional<ProfileEntity> existOptional = profileRepository.findByUsernameAndVisibleIsTrue(dto.getUsername());
        if (existOptional.isPresent()) {
//            throw new AppBadException("Username already exists");

            ProfileEntity existsProfile = existOptional.get();
            if (existsProfile.getStatus().equals(ProfileStatusEnum.NOT_ACTIVE)) {
                profileRoleService.deleteByProfileId(existsProfile.getId());
                profileRepository.deleteById(existsProfile.getId()); // delete
            } else {
                throw new AppBadException("Username already exists");
            }
        }
        // create profile
        ProfileEntity profile = new ProfileEntity();
        profile.setName(dto.getName());
        profile.setSurname(dto.getSurname());
        profile.setUsername(dto.getUsername());
        profile.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        profile.setVisible(true);
        profile.setStatus(ProfileStatusEnum.NOT_ACTIVE);
        profile.setCreatedDate(LocalDateTime.now());
        profileRepository.save(profile);
        // create profile roles
        profileRoleService.create(profile.getId(), ProfileRoleEnum.ROLE_USER);
        // send sms to amail or phone
        // Email Send
        //emailSendingService.sendRegistrationStyledEmail(profile.getUsername());
        smsSenderService.sendRegistrationSms(profile.getUsername());
        return "Tastiqlash kodi ketdi mazgi qara.";
    }

    public AuthResponseDTO login(AuthorizationDTO dto) {
        Optional<ProfileEntity> profileOptional = profileRepository.findByUsernameAndVisibleIsTrue(dto.getUsername());
        if (profileOptional.isEmpty()) {
            throw new AppBadException("Username or password wrong");
        }
        ProfileEntity entity = profileOptional.get();
        if (!bCryptPasswordEncoder.matches(dto.getPassword(), entity.getPassword())) {
            throw new AppBadException("Username or password wrong");
        }
        if (!entity.getStatus().equals(ProfileStatusEnum.ACTIVE)) {
            throw new AppBadException("User in wrong status");
        }
        AuthResponseDTO response = new AuthResponseDTO();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setSurname(entity.getSurname());
        response.setUsername(entity.getUsername());
        response.setRoleList(profileRoleService.getByProfileId(entity.getId()));
        response.setJwt(JwtUtil.encode(entity.getId(), entity.getUsername(), response.getRoleList()));
        return response;
    }


    public String regSmsVerification(String username, Integer smsCode) {
        Optional<ProfileEntity> existOptional = profileRepository.findByUsernameAndVisibleIsTrue(username);
        if (existOptional.isEmpty()) {
            throw new AppBadException("Username not found");
        }
        ProfileEntity profile = existOptional.get();
        if (!profile.getStatus().equals(ProfileStatusEnum.NOT_ACTIVE)) {
            throw new AppBadException("Username int wrong status");
        }
        // check fo sms code and time
//        if (emailHistoryService.isSmsSendToAccount(username, smsCode)) {
        if (smsHistoryService.isSmsSendToPhone(username, smsCode)) {
            profile.setStatus(ProfileStatusEnum.ACTIVE);
            profileRepository.save(profile);
            return "Verification successfully completed";
        }
        throw new AppBadException("Not completed");
    }

}
