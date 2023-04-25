using Android_Web_Server_Begin.Data;
using Android_Web_Server_Begin.Data.Entities;
using Android_Web_Server_Begin.Interfaces;

namespace Android_Web_Server_Begin.Repositorys
{
    public class CategoryRepository : GenericRepository<Category>, ICategoryRepository
    {
        public CategoryRepository(MyDBContextEF context) : base(context)
        {
        }

        public IQueryable<Category> Products => GetAll();

        public async Task CreateCategory(Category entity)
        {
            await Create(entity);
        }
    }
}
