using Android_Web_Server_Begin.Data.DTO;
using Android_Web_Server_Begin.Data.Entities;
using AutoMapper;
using AutoMapper.Internal.Mappers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Unit_Data.AutoMapper
{
    public class AutoMapperProfile : Profile
    {
        public AutoMapperProfile()
        {
            CreateMap<AddCategory, Category>();
            CreateMap<Category, AddCategory>();
        }
    }
}
