using Android_Web_Server_Begin.Data.Entities;
using Microsoft.EntityFrameworkCore;
using System;

namespace Android_Web_Server_Begin.Data
{
    public class MyDBContextEF : DbContext
    {
        //protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        //{
        //    IConfigurationRoot configuration = new ConfigurationBuilder()
        //      .SetBasePath(Directory.GetCurrentDirectory())
        //      .AddJsonFile("appsettings.json")
        //      .Build();
        //    var connectionString = configuration.GetConnectionString("DefaultConnection");
        //    optionsBuilder.UseSqlServer(connectionString);
        //}

        public MyDBContextEF(DbContextOptions<MyDBContextEF> options)
            : base(options)
        { }

        /*protected override void OnModelCreating(ModelBuilder builder)
        {
            base.OnModelCreating(builder);
        }*/


        public DbSet<Category> categories { get; set; }
    }
}
