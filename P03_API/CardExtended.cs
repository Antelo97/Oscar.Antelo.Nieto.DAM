using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace P03_API
{
    internal class CardExtended
    {
        public string name { get; set; }
        public string type { get; set; }
        public string color { get; set; }
        public string stage { get; set; }
        public string digi_type { get; set; }
        public string attribute { get; set; }
        public int level { get; set; }
        public int play_cost { get; set; }
        public int evolution_cost { get; set; }
        public string cardrarity { get; set; }
        public string artist { get; set; }
        public int dp { get; set; }
        public string cardnumber { get; set; }
        public string maineffect { get; set; }
        public string soureeffect { get; set; }
        //public string set_name { get; set; }
        public List<string> card_sets { get; set; }
        public string image_url { get; set; }
    }
}
