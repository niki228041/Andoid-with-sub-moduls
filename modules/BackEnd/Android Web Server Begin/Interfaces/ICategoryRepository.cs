using Android_Web_Server_Begin.Data.Entities;

namespace Android_Web_Server_Begin.Interfaces
{
    public interface ICategoryRepository : IGenericRepository<Category, int>
    {
        public Task CreateCategory(Category entity);
    }
}
