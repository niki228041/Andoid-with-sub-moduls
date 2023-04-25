using Android_Web_Server_Begin.Data.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Android_Web_Server_Begin.Interfaces
{
    public interface IGenericRepository<TEntity,T> where TEntity : class , IEntity<T>
    {
        IQueryable<TEntity> GetAll();
        Task<TEntity> GetById(T id);
        Task Update(TEntity entity);
        Task Create(TEntity entity);
        Task Delete(T id);
    }
}
