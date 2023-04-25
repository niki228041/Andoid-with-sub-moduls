using Android_Web_Server_Begin.Data;
using Android_Web_Server_Begin.Interfaces;
using Android_Web_Server_Begin.Repositorys;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.FileProviders;
using System;
using Unit_Data.AutoMapper;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.

builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

// Add services to the container.
builder.Services.AddDbContext<MyDBContextEF>(options =>
    options.UseSqlServer(builder.Configuration.GetConnectionString("DefaultConnection")));

builder.Services.AddAutoMapper(typeof(AutoMapperProfile));

//builder.Services.AddDbContextPool<MyDBContextEF>(
//    o => o.UseSqlServer(builder.Configuration.GetConnectionString("DefaultConnection")));

builder.Services.AddScoped<ICategoryRepository, CategoryRepository>();

var app = builder.Build();


// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseCors(options => options
    .WithOrigins("http://localhost:3000", "http://localhost:3001", "http://194.44.93.225", "http://10.7.101.243", "http://52.188.227.148", "http://40.76.116.183", "http://192.168.0.104")
    .AllowAnyHeader()
    .AllowCredentials()
    .AllowAnyMethod()
    );

var dir = Path.Combine(Directory.GetCurrentDirectory(), "images");
if(!Directory.Exists(dir))
{
    Directory.CreateDirectory(dir);
}

app.UseStaticFiles(new StaticFileOptions
{
    FileProvider = new PhysicalFileProvider(dir),
    RequestPath="/images"
});

app.UseAuthorization();

app.MapControllers();

app.Run();
