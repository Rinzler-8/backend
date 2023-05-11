package com.vti.controllers;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vti.dto.AccountDTO;
import com.vti.entity.User;
import com.vti.form.AccountFormForCreating;
import com.vti.form.AccountFormForUpdating;
import com.vti.security.service.IUserService;

@RestController
@RequestMapping(value = "api/v1/accounts")
@CrossOrigin("*")
public class UserController {

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	private IUserService userService;

	@GetMapping(value = "/email/{email}")
	public ResponseEntity<?> existsUserByEmail(@PathVariable(name = "email") String email) {
		// get entity
		boolean result = userService.existsUserByEmail(email);

		// return result
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping(value = "/username/{username}")
	public ResponseEntity<?> existsUserByUserName(@PathVariable(name = "username") String username) {
		// get entity
		boolean result = userService.existsUserByUsername(username);

		// return result
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping()
	public ResponseEntity<?> getAllaccount(Pageable pageable, @RequestParam(required = false) String search) {
		Page<User> accountPage_DB = userService.getAllAccounts(pageable, search);
		// Dữ liệu lấy ở DB, đã được thực hiện phân trang và sort dữ liệu

		// Chuyển đổi dữ liệu
		Page<AccountDTO> accountPage_Dtos = accountPage_DB.map(new Function<User, AccountDTO>() {
			@Override
			public AccountDTO apply(User user) {
				AccountDTO AccountDTO = new AccountDTO();
				AccountDTO.setId(user.getId());
				AccountDTO.setEmail(user.getEmail());
				AccountDTO.setUsername(user.getUsername());
				AccountDTO.setFirstName(user.getFirstName());
				AccountDTO.setMobile(user.getMobile());
				AccountDTO.setUrlAvatar(user.getUrlAvatar());
				AccountDTO.setAddress(user.getAddress());
				AccountDTO.setLastName(user.getLastName());
				AccountDTO.setRole(user.getRole());
				AccountDTO.setStatus(user.getStatus());
				AccountDTO.setPassword(user.getPassword());
				return AccountDTO;
			}

		});

		return new ResponseEntity<>(accountPage_Dtos, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getAccountByID(@PathVariable(name = "id") int id) {
		try {
			User accountDB = userService.getAccountByID(id);

			// convert accountDB --> AccountDTO

			AccountDTO AccountDTO = new AccountDTO();
			AccountDTO.setId(accountDB.getId());
			AccountDTO.setEmail(accountDB.getEmail());
			AccountDTO.setUsername(accountDB.getUsername());
			AccountDTO.setFirstName(accountDB.getFirstName());
			AccountDTO.setLastName(accountDB.getLastName());
			AccountDTO.setMobile(accountDB.getMobile());
			AccountDTO.setAddress(accountDB.getAddress());
			AccountDTO.setUrlAvatar(accountDB.getUrlAvatar());
			AccountDTO.setRole(accountDB.getRole());
			AccountDTO.setStatus(accountDB.getStatus());
			AccountDTO.setPassword(accountDB.getPassword());
			return new ResponseEntity<>(AccountDTO, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
		}

	}

//	Thêm mới sản phẩm
	@PostMapping(value = "/create")
	public ResponseEntity<?> createNewAccount(@RequestBody AccountFormForCreating accountNewForm) {
		try {
//			Thêm mới Account
//			Sau khi thêm mới, trả về thông tin Account vừa thêm
			User accountNew = userService.createAccount(accountNewForm);

//			Convert
			AccountDTO accountNewDto = new AccountDTO();
			accountNewDto.setId(accountNew.getId());
			accountNewDto.setEmail(accountNew.getEmail());
			accountNewDto.setPassword(encoder.encode(accountNew.getPassword()));
			accountNewDto.setUsername(accountNew.getUsername());
			accountNewDto.setFirstName(accountNew.getFirstName());
			accountNewDto.setLastName(accountNew.getLastName());
			accountNewDto.setRole(accountNew.getRole());
			accountNewDto.setMobile(accountNew.getMobile());
			accountNewDto.setUrlAvatar(accountNew.getUrlAvatar());

			return new ResponseEntity<>(accountNewDto, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("Can not create new account", HttpStatus.BAD_REQUEST);
		}

	}

//	Xóa sản phẩm theo id
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteAccountById(@PathVariable(name = "id") int id) {
		try {

			userService.deleteAccountById(id);

			return new ResponseEntity<>("Delete account ok", HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>("Can not delete account", HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateAccount(@PathVariable(name = "id") short id,
			@RequestBody AccountFormForUpdating form) {
		userService.updateAccount(id, form);
		return new ResponseEntity<String>("Update successfully!", HttpStatus.OK);
	}

	@GetMapping(value = "/avatar/{id}")
	public ResponseEntity<?> getAvatar(@PathVariable(name = "id") int id) {
		HttpHeaders headers = new HttpHeaders();
//        headers.add("content-disposition", "inline;filename=" + fileName[fileName.length - 1]);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		return ResponseEntity.ok().headers(headers).contentType(MediaType.IMAGE_JPEG).body(userService.getAvatar(id));

	}

//	@PutMapping(value = "/block/{id}")
//	@PreAuthorize("hasAnyAuthority('ADMIN')")
//	public ResponseEntity<?> upBlockExpDate(@PathVariable(name = "id") int id,
//			@RequestParam(name = "blockExpDate") Integer date) {
//		userService.upBlockExpDate(id, date);
//		return null;
//	}
//
//	@PutMapping(value = "/unblock/{id}")
//	public ResponseEntity<?> unBlockExpDate(@PathVariable(name = "id") int id) {
//		userService.unBlockExpDate(id);
//		return null;
//	}
}
