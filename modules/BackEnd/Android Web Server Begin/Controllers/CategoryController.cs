using Android_Web_Server_Begin.Data.DTO;
using Android_Web_Server_Begin.Data.Entities;
using Android_Web_Server_Begin.Interfaces;
using AutoMapper;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace Android_Web_Server_Begin.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class CategoryController : ControllerBase
    {


        readonly ICategoryRepository _categoryRepository;
        readonly IMapper _mapper;

        public CategoryController(ICategoryRepository categoryRepository,IMapper mapper)
        {
            _categoryRepository = categoryRepository;
            _mapper = mapper;
        }
    
        [HttpGet(Name = "GetCategories")]
        public IQueryable<Category> GetCategories()
        {
            return _categoryRepository.GetAll();
        }

        [HttpPost(Name = "AddCategory")]
        public async Task<IActionResult> AddCategory(AddCategory newCategory)
        {
            var categoryentity = _mapper.Map<Category>(newCategory);
            await _categoryRepository.CreateCategory(categoryentity);
            return Ok(newCategory);
        }
    }
}
