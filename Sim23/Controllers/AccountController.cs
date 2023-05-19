using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Sim23.Abastract;
using Sim23.Constants;
using Sim23.Data;
using Sim23.Data.Entites.Identity;
using Sim23.Helpers;
using Sim23.Models;

namespace Sim23.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class AccountController : ControllerBase
    {
        private readonly IJwtTokenService _jwtTokenService;
        private readonly UserManager<UserEntity> _userManager;
        private readonly AppEFContext _appEFContext;

        public AccountController(IJwtTokenService jwtTokenService, UserManager<UserEntity> userManager, AppEFContext appEFContext)
        {
            _jwtTokenService = jwtTokenService;
            _userManager = userManager;
            _appEFContext = appEFContext;
        }
        [HttpPost("login")]
        public async Task<IActionResult> Login([FromBody] LoginViewModel model)
        {
            var user = await _userManager.FindByEmailAsync(model.Email);
            if (user != null)
            {
                var isPasswordValid = await _userManager.CheckPasswordAsync(user, model.Password);
                if (!isPasswordValid)
                {
                    return BadRequest();
                }
                var token = await _jwtTokenService.CreateToken(user);
                return Ok(new { token });
            }
            return BadRequest();
        }


        [HttpPost("register")]
        public async Task<IActionResult> Register([FromBody] RegisterUserViewModel model)
        {

            string imageName = String.Empty;

            if (model.ImageBase64 != null)
            {
                imageName = ImageWorker.SaveImage(model.ImageBase64);
            }

            UserEntity user = new UserEntity()
            {
                FirstName = model.FirstName,
                LastName = model.LastName,
                UserName = model.Email,
                Email = model.Email,
                Image = imageName
            };

            var result = await _userManager.CreateAsync(user, model.Password);
            if (result.Succeeded)
            {
                result = await _userManager.AddToRoleAsync(user, Roles.User);
                return Ok();
            }
            else
            {
                return BadRequest();
            }
        }


        [HttpPost("changeUserInfo")]
        public async Task<IActionResult> ChangeUserInfo([FromBody] ChangeUserInfoViewModel model)
        {

            string imageName = String.Empty;

            if (model.ImageBase64 != null)
            {
                imageName = ImageWorker.SaveImage(model.ImageBase64);
            }



            var user_ = await _userManager.FindByIdAsync(model.Id.ToString());

            if (user_ != null)
            {
                user_.FirstName = model.FirstName;
                user_.LastName = model.LastName;

                user_.Email = model.Email;

                if (model.ImageBase64 != null)
                    user_.Image = imageName;

                await _userManager.UpdateAsync(user_);
                return Ok(user_);

            }
            return BadRequest();
        }



        [HttpPost("getUserInfoByEmail")]
        public async Task<IActionResult> GetUserInfoByEmail([FromBody] ByEmail model)
        {
            var user_ = await _userManager.FindByEmailAsync(model.Email);
            
            UserViewModel userView = new UserViewModel();

            userView.LastName = user_.LastName;
            userView.Email = user_.Email;
            userView.FirstName = user_.FirstName;
            userView.Image = user_.Image;
            userView.Id = user_.Id;

            return Ok(userView);
        }

    }
}
