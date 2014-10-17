using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace TP01
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
           
        }

        #region TP01_Part01
        private void btnClick_Click(object sender, EventArgs e)
        {
            MessageBox.Show("HelloWord");
            
        }
        #endregion

        #region TP01_Part02
        private void Debug_Click(object sender, EventArgs e)
        {
            int i = 0;
            int j = 0;

            int test = i++;
            test = ++j;

            AfficherMessage("Test = " + test);
        }

        private void AfficherMessage(String msg)
        {
            MessageBox.Show(msg);
        }
        #endregion
    }
}
