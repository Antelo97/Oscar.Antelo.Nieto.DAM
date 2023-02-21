using Practica01.Properties;
using System;
using System.Collections;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System;
using System.Threading.Tasks;
using System.Threading;
using static System.Windows.Forms.VisualStyles.VisualStyleElement;
using static System.Windows.Forms.VisualStyles.VisualStyleElement.StartPanel;
using static System.Windows.Forms.VisualStyles.VisualStyleElement.TreeView;
using Practica01.Idiomas;
using System.Xml;

namespace Practica01
{
    public partial class FindPairs : Form
    {
        List<Image> images = new List<Image>() {Resources.Athletic_Bilbao, Resources.Atletico_de_Madrid, Resources.Barcelona, Resources.Betis,
        Resources.Real_Madrid, Resources.Real_Sociedad, Resources.Sevilla, Resources.Valencia};

        List<int> nums = new List<int>() {0, 1, 2, 3, 4, 5, 6, 7, 0, 1, 2, 3, 4, 5, 6, 7};
        int[] pattern = new int[16];

        List<PictureBox> boxes = new List<PictureBox>();
        List<PictureBox> pair = new List<PictureBox>();

        String username = "";
        bool difficulty = false;
        String difficultyMode = "";
        int difficultyModeNumber = -1;
        int mistakes = 0;
        int score = -1;
        int time = -1;
        int timeSpent = 0;
        int penalty = -1;
        double rechargeTime = 1000;
        int findCouples = 0;

        BindingList<Ranking> listRanking = new BindingList<Ranking>();
        
        public FindPairs()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            asignarPatron();

            boxes = new List<PictureBox> { pbImg01, pbImg02, pbImg03, pbImg04, pbImg05, pbImg06, pbImg07, pbImg08, 
                pbImg09, pbImg10, pbImg11, pbImg12, pbImg13, pbImg14, pbImg15, pbImg16 };

            dgvRanking.DataSource = listRanking;
        }
  
        private void asignarPatron()
        {
            Random r = new Random();

            for (int i = 0; i < pattern.Length; i++)
            {
                int random = r.Next(0, nums.Count);
                
                pattern[i] = nums[random];
                nums.RemoveAt(random);
            }
        }

        private void tbUserName_TextChanged(object sender, EventArgs e)
        {
            username = tbUserName.Text;
            dgvRanking.Visible = false;
        }

        private void btnEasy_Click(object sender, EventArgs e)
        {
            difficulty = true;
            difficultyMode = Idiomas.Idioma.easy;
            difficultyModeNumber = 1;
            time = 240000;
            penalty = 4000;

            lblMode.Visible = true;
            lblMode.Text = Idiomas.Idioma.easyMode;
            lblInfoMode.Visible = true;
            lblInfoMode.Text = Idiomas.Idioma.infoMode;
            lblDataMode.Visible = true;
            lblDataMode.Text = (time / 1000).ToString() + " seg\n" + "- " + (penalty / 1000).ToString() + " seg";

            dgvRanking.Visible = false;
        }

        private void btnMedium_Click(object sender, EventArgs e)
        {
            difficulty = true;
            difficultyMode = Idiomas.Idioma.medium;
            difficultyModeNumber = 2;
            time = 220000;
            penalty = 6000;

            lblMode.Visible = true;
            lblMode.Text = Idiomas.Idioma.mediumMode;
            lblInfoMode.Visible = true;
            lblInfoMode.Text = Idiomas.Idioma.infoMode;
            lblDataMode.Visible = true;
            lblDataMode.Text = (time / 1000).ToString() + " seg\n" + "- " + (penalty / 1000).ToString() + " seg";

            dgvRanking.Visible = false;
        }

        private void btnHard_Click(object sender, EventArgs e)
        {
            difficulty = true;
            difficultyMode = Idiomas.Idioma.hard;
            difficultyModeNumber = 3;
            time = 200000;
            penalty = 8000;

            lblMode.Visible = true;
            lblMode.Text = Idiomas.Idioma.hardMode;
            lblInfoMode.Visible = true;
            lblInfoMode.Text = Idiomas.Idioma.infoMode;
            lblDataMode.Visible = true;
            lblDataMode.Text = (time / 1000).ToString() + " seg\n" + "- " + (penalty / 1000).ToString() + " seg";

            dgvRanking.Visible = false;
        }

        private void btnExtreme_Click(object sender, EventArgs e)
        {
            difficulty = true;
            difficultyMode = Idiomas.Idioma.extreme;
            difficultyModeNumber = 4;
            time = 180000;
            penalty = 10000;

            lblMode.Visible = true;
            lblMode.Text = Idiomas.Idioma.extremeMode;
            lblInfoMode.Visible = true;
            lblInfoMode.Text = Idiomas.Idioma.infoMode;
            lblDataMode.Visible = true;
            lblDataMode.Text = (time / 1000).ToString() + " seg\n" + "- " + (penalty / 1000).ToString() + " seg";

            dgvRanking.Visible = false;
        }

        private void tbRechargeTime_Scroll(object sender, EventArgs e)
        {
            if (tbRechargeTime.Value == 0)
            {
                rechargeTime = 250;
                lblValueRechargeTime.Text = "0.25 seg";
            }
            else if (tbRechargeTime.Value == 1)
            {
                rechargeTime = 500;
                lblValueRechargeTime.Text = "0.50 seg";

            }
            else if (tbRechargeTime.Value == 2)
            {
                rechargeTime = 750;
                lblValueRechargeTime.Text = "0.75 seg";

            }
            else if (tbRechargeTime.Value == 3)
            {
                rechargeTime = 1000;
                lblValueRechargeTime.Text = "1.00 seg";

            }
            else if (tbRechargeTime.Value == 4)
            {
                rechargeTime = 2000;
                lblValueRechargeTime.Text = "2.00 seg";

            }
            else if (tbRechargeTime.Value == 5)
            {
                rechargeTime = 3000;
                lblValueRechargeTime.Text = "3.00 seg";
            }

            dgvRanking.Visible = false;
        }

        private void cbLanguage_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (cbLanguage.SelectedIndex == 0)
            {
                Thread.CurrentThread.CurrentUICulture = new CultureInfo("es-ES");
                traducirIdioma();
            }
            else if (cbLanguage.SelectedIndex == 1)
            {
                Thread.CurrentThread.CurrentUICulture = new CultureInfo("en-US");
                traducirIdioma();
            }
            else if (cbLanguage.SelectedIndex == 2)
            {
                Thread.CurrentThread.CurrentUICulture = new CultureInfo("fr-PT");
                traducirIdioma();
            }
            else if (cbLanguage.SelectedIndex == 3)
            {
                Thread.CurrentThread.CurrentUICulture = new CultureInfo("as-SA");
                traducirIdioma();
            }
            else if (cbLanguage.SelectedIndex == 4)
            {
                Thread.CurrentThread.CurrentUICulture = new CultureInfo("zh-CN");
                traducirIdioma();
            }

            dgvRanking.Visible = false;
        }

        private void traducirIdioma()
        {

            if (difficultyModeNumber == 1)
            {
                difficultyMode = Idiomas.Idioma.easy;
            }
            else if (difficultyModeNumber == 2)
            {
                difficultyMode = Idiomas.Idioma.medium;
            }
            else if (difficultyModeNumber == 3)
            {
                difficultyMode = Idiomas.Idioma.hard;
            }
            else if (difficultyModeNumber == 4)
            {
                difficultyMode = Idiomas.Idioma.extreme;
            }

            btnStart.Text = Idiomas.Idioma.start;
            lblUsername.Text = Idiomas.Idioma.username;
            lblDifficulty.Text = Idiomas.Idioma.difficulty;
            btnEasy.Text = Idiomas.Idioma.easy;
            btnMedium.Text = Idiomas.Idioma.medium;
            btnHard.Text = Idiomas.Idioma.hard;
            btnExtreme.Text = Idiomas.Idioma.extreme;
            lblRechargeTIme.Text = Idiomas.Idioma.rechargeTime;

            lblLanguage.Text = Idiomas.Idioma.language;
            cbLanguage.Items.Clear();
            cbLanguage.Items.Add(Idiomas.Idioma.spanish);
            cbLanguage.Items.Add(Idiomas.Idioma.english);
            cbLanguage.Items.Add(Idiomas.Idioma.french);
            cbLanguage.Items.Add(Idiomas.Idioma.arab);
            cbLanguage.Items.Add(Idiomas.Idioma.chinese);
            lblCreator.Text = Idiomas.Idioma.creator;

            lblFindPairs.Text = Idiomas.Idioma.findPairs;
            lblTimeLeft.Text = Idiomas.Idioma.timeLeft;

            lblCurrentGame.Text = Idiomas.Idioma.currentGame;
            lblInfoCurrentGame.Text = Idiomas.Idioma.infoCurrentGame;
            lblMode.Text = Idiomas.Idioma.easyMode;
            lblInfoMode.Text = Idiomas.Idioma.infoMode;
            lblSummaryGame.Text = Idiomas.Idioma.summaryGame;
            lblInfoSummaryGame.Text = Idiomas.Idioma.infoSummaryGame;
            btnRanking.Text = Idiomas.Idioma.ranking;
            btnClearRanking.Text = Idiomas.Idioma.clearRanking;
            btnExit.Text = Idiomas.Idioma.exit;
        }

        private void btnStart_Click(object sender, EventArgs e)
        {
            if(username.Equals("") && !difficulty)
            {
                MessageBox.Show(Idiomas.Idioma.errorUsername + "\n" + Idiomas.Idioma.errorDifficulty, Idiomas.Idioma.errorMessage,
                    MessageBoxButtons.OKCancel,
                    MessageBoxIcon.Error);
            }
            else if (username.Equals(""))
            {
                MessageBox.Show(Idiomas.Idioma.errorUsername, Idiomas.Idioma.errorMessage,
                    MessageBoxButtons.OKCancel,
                    MessageBoxIcon.Error);
            }
            else if (!difficulty)
            {
                MessageBox.Show(Idiomas.Idioma.errorDifficulty, Idiomas.Idioma.errorMessage,
                    MessageBoxButtons.OKCancel,
                    MessageBoxIcon.Error);
            }
            else
            {
                btnStart.Enabled = false;
                btnEasy.Enabled = false;
                btnMedium.Enabled = false;
                btnHard.Enabled = false;
                btnExtreme.Enabled = false;
                btnRanking.Enabled = false;
                tbUserName.Enabled = false;
                tbRechargeTime.Enabled = false;
                cbLanguage.Enabled = false;

                int sTime = time / 1000;
                timer.Enabled = true;
                tlpGameBoard.Visible = true;
                lblSecondsLeft.Text = sTime.ToString();
                pbCountdown.Maximum = sTime;
                pbCountdown.Value = sTime;

                lblCurrentGame.Visible = true;
                lblCurrentGame.Text = Idiomas.Idioma.currentGame;
                lblInfoCurrentGame.Visible = true;
                lblInfoCurrentGame.Text = Idiomas.Idioma.infoCurrentGame;
                lblDataCurrentGame.Visible = true;
                lblDataCurrentGame.Text = username + "\n" + difficultyMode + "\n" + (rechargeTime / 1000).ToString() + " seg\n" + mistakes.ToString() + "\n" + score.ToString();

                lblMode.Visible = true;
                lblInfoMode.Visible = true;
                lblDataMode.Visible = true;

                dgvRanking.Visible = false;
            }
        }

        private void comprobarPareja(List<PictureBox> pair)
        {
            var t = Task.Run(async delegate
            {
                await Task.Delay(Convert.ToInt32(rechargeTime));
            });

            t.Wait();

            if (pair.Count == 2)
            {
                evaluarPareja(pair[0], pair[1]);
            }
        }

        private void evaluarPareja(PictureBox x, PictureBox y)
        {
            if(x.Image == y.Image)
            {
                x.Enabled = false;
                y.Enabled = false;
                x.Cursor = default;
                y.Cursor = default;
                findCouples++;
            }
            else
            {
                x.Image = Resources.Interrogacion;
                y.Image = Resources.Interrogacion;
                mistakes++;

                if((pbCountdown.Value - (penalty / 1000)) >= 0)
                {
                    pbCountdown.Value = pbCountdown.Value - (penalty / 1000);
                }
                else
                {
                    pbCountdown.Value = 0;
                }
            }

            pair.Clear();
        }

        private void comprobarEstado()
        {
            if (findCouples == 8)
            {
                lblSummaryGame.Visible = true;
                lblSummaryGame.Text = Idiomas.Idioma.summaryGame;
                lblInfoSummaryGame.Visible = true;
                lblInfoSummaryGame.Text = Idiomas.Idioma.infoSummaryGame;
                lblDataSummaryGame.Visible = true;
                lblDataSummaryGame.Text = timeSpent.ToString() + " seg\n" + mistakes.ToString() + "\n" + score.ToString();

                listRanking.Add(new Ranking { Username = username, Score = score, Difficulty_Mode = difficultyMode});

                timer.Enabled = false;

                reiniciarJuego();
            }
        }

        private void pbImg01_Click(object sender, EventArgs e)
        {
            pbImg01.Image = images[pattern[0]];
            pair.Add(pbImg01);
            comprobarPareja(pair);
        }
        
        private void pbImg02_Click(object sender, EventArgs e)
        {
            pbImg02.Image = images[pattern[1]];
            pair.Add(pbImg02);
            comprobarPareja(pair);
        }

        private void pbImg03_Click(object sender, EventArgs e)
        {
            pbImg03.Image = images[pattern[2]];
            pair.Add(pbImg03);
            comprobarPareja(pair);
        }

        private void pbImg04_Click(object sender, EventArgs e)
        {
            pbImg04.Image = images[pattern[3]];
            pair.Add(pbImg04);
            comprobarPareja(pair);
        }

        private void pbImg05_Click(object sender, EventArgs e)
        {
            pbImg05.Image = images[pattern[4]];
            pair.Add(pbImg05);
            comprobarPareja(pair);
        }

        private void pbImg06_Click(object sender, EventArgs e)
        {
            pbImg06.Image = images[pattern[5]];
            pair.Add(pbImg06);
            comprobarPareja(pair);
        }

        private void pbImg07_Click(object sender, EventArgs e)
        {
            pbImg07.Image = images[pattern[6]];
            pair.Add(pbImg07);
            comprobarPareja(pair);
        }

        private void pbImg08_Click(object sender, EventArgs e)
        {
            pbImg08.Image = images[pattern[7]];
            pair.Add(pbImg08);
            comprobarPareja(pair);
        }

        private void pbImg09_Click(object sender, EventArgs e)
        {
            pbImg09.Image = images[pattern[8]];
            pair.Add(pbImg09);
            comprobarPareja(pair);
        }

        private void pbImg10_Click(object sender, EventArgs e)
        {
            pbImg10.Image = images[pattern[9]];
            pair.Add(pbImg10);
            comprobarPareja(pair);
        }

        private void pbImg11_Click(object sender, EventArgs e)
        {
            pbImg11.Image = images[pattern[10]];
            pair.Add(pbImg11);
            comprobarPareja(pair);
        }

        private void pbImg12_Click(object sender, EventArgs e)
        {
            pbImg12.Image = images[pattern[11]];
            pair.Add(pbImg12);
            comprobarPareja(pair);
        }

        private void pbImg13_Click(object sender, EventArgs e)
        {
            pbImg13.Image = images[pattern[12]];
            pair.Add(pbImg13);
            comprobarPareja(pair);
        }

        private void pbImg14_Click(object sender, EventArgs e)
        {
            pbImg14.Image = images[pattern[13]];
            pair.Add(pbImg14);
            comprobarPareja(pair);
        }

        private void pbImg15_Click(object sender, EventArgs e)
        {
            pbImg15.Image = images[pattern[14]];
            pair.Add(pbImg15);
            comprobarPareja(pair);
        }

        private void pbImg16_Click(object sender, EventArgs e)
        {
            pbImg16.Image = images[pattern[15]];
            pair.Add(pbImg16);
            comprobarPareja(pair);
        }

        private void reiniciarJuego()
        {
            pair.Clear();

            for (int i = 0; i < boxes.Count; i++)
            {
                // reseteamos los Picture Box
                boxes[i].Image = Resources.Interrogacion;
                boxes[i].Enabled = true;
                boxes[i].Cursor = Cursors.Hand;

                // reseteamos el ArrayList de carga
                if(nums.Count > 7)
                {
                    nums.Add(i-8);
                }
                else
                {
                    nums.Add(i);
                }
            }

            asignarPatron();

            mistakes = 0;
            score = 0;
            timeSpent = 0;
            penalty = 0;
            findCouples = 0;

            btnStart.Enabled = true;
            btnEasy.Enabled = true;
            btnMedium.Enabled = true;
            btnHard.Enabled = true;
            btnExtreme.Enabled = true;
            btnRanking.Enabled = true;
            tbUserName.Enabled = true;
            tbRechargeTime.Enabled = true;
            cbLanguage.Enabled = true;

            int sTime = time / 1000;
            lblSecondsLeft.Text = "---";
            pbCountdown.Maximum = sTime;
            pbCountdown.Value = sTime;

            tlpGameBoard.Visible = false;
            lblCurrentGame.Visible = false;
            lblInfoCurrentGame.Visible = false;
            lblDataCurrentGame.Visible = false;
            lblSummaryGame.Visible = false;
            lblInfoSummaryGame.Visible = false;
            lblDataSummaryGame.Visible = false;
        }

        private void btnRanking_Click(object sender, EventArgs e)
        {
            dgvRanking.Visible = true;

            lblCurrentGame.Visible = false;
            lblInfoCurrentGame.Visible = false;
            lblDataCurrentGame.Visible = false;

            lblMode.Visible = false;
            lblInfoMode.Visible = false;
            lblDataMode.Visible = false;

            lblSummaryGame.Visible = false;
            lblInfoSummaryGame.Visible = false;
            lblDataSummaryGame.Visible = false;
        }

        private void btnClearRanking_Click(object sender, EventArgs e)
        {
            listRanking.Clear();
        }

        private void btnExit_Click(object sender, EventArgs e)
        {
            Close();
        }

        private void timer_Tick(object sender, EventArgs e)
        {
            if (pbCountdown.Value == 0)
            {
                timer.Enabled = false;

                score = 0;
                lblSummaryGame.Visible = true;
                lblSummaryGame.Text = Idiomas.Idioma.summaryGame;
                lblInfoSummaryGame.Visible = true;
                lblInfoSummaryGame.Text = Idiomas.Idioma.infoSummaryGame;
                lblDataSummaryGame.Visible = true;
                lblDataSummaryGame.Text = timeSpent.ToString() + " seg\n" + mistakes.ToString() + "\n" + score.ToString();

                MessageBox.Show(Idiomas.Idioma.endgameDefeat, Idiomas.Idioma.endgameMessage,
                    MessageBoxButtons.OKCancel,
                    MessageBoxIcon.Information);

                reiniciarJuego();
            }
            else if(findCouples == 8)
            {
                timer.Enabled = false;

                lblSummaryGame.Visible = true;
                lblSummaryGame.Text = Idiomas.Idioma.summaryGame;
                lblInfoSummaryGame.Visible = true;
                lblInfoSummaryGame.Text = Idiomas.Idioma.infoSummaryGame;
                lblDataSummaryGame.Visible = true;
                lblDataSummaryGame.Text = timeSpent.ToString() + " seg\n" + mistakes.ToString() + "\n" + score.ToString();

                listRanking.Add(new Ranking { Username = username, Score = score, Difficulty_Mode = difficultyMode });

                MessageBox.Show(Idiomas.Idioma.endgameVictory, Idiomas.Idioma.endgameMessage,
                    MessageBoxButtons.OKCancel,
                    MessageBoxIcon.Information);

                reiniciarJuego();
            }
            else
            {
                pbCountdown.Value--;
                timeSpent++;
                lblSecondsLeft.Text = pbCountdown.Value.ToString() + " seg";
                score = (pbCountdown.Value) * 10;
                lblDataCurrentGame.Text = username + "\n" + difficultyMode + "\n" + (rechargeTime / 1000).ToString() + " seg\n" + mistakes.ToString() + "\n" + score.ToString();
            }
        }


        private void panel2_Paint(object sender, PaintEventArgs e)
        {

        }

        private void lblCurrentGame_Click(object sender, EventArgs e)
        {

        }

        private void lblValueRechargeTime_Click(object sender, EventArgs e)
        {

        }

        private void lblInfoSummaryGame_Click(object sender, EventArgs e)
        {

        }

        private void flpGameBoard_Paint(object sender, PaintEventArgs e)
        {

        }

        private void tableLayoutPanel1_Paint(object sender, PaintEventArgs e)
        {

        }

        private void lblTimeLeft_Click(object sender, EventArgs e)
        {

        }
    }
}
