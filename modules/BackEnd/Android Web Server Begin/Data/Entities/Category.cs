using System.ComponentModel.DataAnnotations.Schema;

namespace Android_Web_Server_Begin.Data.Entities
{
    public class Category : BaseEntity<int>
    {
        public string Name { get; set; }
        public string Details { get; set; }
       
    }
}
