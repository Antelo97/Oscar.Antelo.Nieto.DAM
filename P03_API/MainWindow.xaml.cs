using RestSharp;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Windows.Threading;
using static System.Net.Mime.MediaTypeNames;
using Image = System.Windows.Controls.Image;

namespace P03_API
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        RestClient client = new RestClient("https://digimoncard.io");
        RestRequest requestAllCards = new RestRequest("/api-public/getAllCards.php?sort=name&series=Digimon Card Game&sortdirection=asc", Method.Get);
        RestRequest requestSpecificCard;

        List<Card> allCards;
        List<CardExtended> specificCard;
        List<CardExtended> specificCard1;
        List<CardExtended> specificCard2;
        List<CardExtended> specificCard3;
        String n;
        String card;

        DispatcherTimer timerMemorization = new DispatcherTimer();
        int timeMemorization = 10;
        DispatcherTimer timerGame = new DispatcherTimer();
        int timeGame = 3;

        int score = 0;

        Random rnd = new Random();
        int[] randomNumbers = new int[3];

        public MainWindow()
        {
            InitializeComponent();
            allCards = client.Execute<List<Card>>(requestAllCards).Data;
            showSpecificGrid(gMenu);
        }

        private static Boolean checkForNullAttributes(object obj)
        {
            bool result = true;
            Type type = obj.GetType();
            foreach (PropertyInfo property in type.GetProperties())
            {
                if (property.GetValue(obj) == null)
                {
                    //throw new ArgumentException($"El atributo '{property.Name}' no puede ser nulo.");
                    result = false;
                }
            }
            return result;
        }

        private void showSpecificGrid(Grid grid)
        {
            gMenu.Visibility = Visibility.Hidden;
            gCatalogue.Visibility = Visibility.Hidden;
            gPreGame.Visibility = Visibility.Hidden;
            gOnGame.Visibility = Visibility.Hidden;
            //gDifficulty.Visibility = Visibility.Hidden;

            grid.Visibility = Visibility.Visible;

            //if(grid == gDifficulty)
            //{
            //    gMenu.Visibility = Visibility.Visible;
            //}
        }

        private void btnCatalogue_Click(object sender, RoutedEventArgs e)
        {
            showSpecificGrid(gCatalogue);

            ScrollViewer scrollViewer = (ScrollViewer)this.FindName("svCatalogue");
            StackPanel stackPanel = (StackPanel)this.FindName("spCatalogue");
            ToolTip toolTip = new ToolTip();

            for (int i = 0; i < 20; i++)
            {
                int random = rnd.Next(0, allCards.Count);

                n = allCards[random].name;
                card = allCards[random].cardnumber;
                querySpecificCard(n, card);
                specificCard = client.Execute<List<CardExtended>>(requestSpecificCard).Data;

                if (specificCard == null)
                {

                }
                else
                {
                    Image myImage = new Image();
                    BitmapImage bitmapImage = new BitmapImage(new Uri(specificCard[0].image_url, UriKind.Absolute));
                    myImage.Source = bitmapImage;
                    myImage.Width = 300;
                    myImage.Height = 450;
                    myImage.Margin = new Thickness(10, 10, 10, 10);
                    toolTip.Content = "Name: " + specificCard[0].name + "\n\r"
                        + "Type: " + specificCard[0].type + "\n\r"
                        + "Color: " + specificCard[0].color + "\n\r"
                        + "Stage: " + specificCard[0].stage + "\n\r"
                        + "Digit-type: " + specificCard[0].digi_type + "\n\r"
                        + "Attribute: " + specificCard[0].attribute + "\n\r"
                        + "Level: " + System.Convert.ToInt32(specificCard[0].level) + "\n\r"
                        + "Play-cost: " + System.Convert.ToInt32(specificCard[0].play_cost) + "\n\r"
                        + "Evolution-cost: " + System.Convert.ToInt32(specificCard[0].evolution_cost) + "\n\r"
                        + "Cardrarity: " + specificCard[0].cardrarity + "\n\r"
                        + "Artist: " + specificCard[0].artist + "\n\r"
                        + "Dp: " + System.Convert.ToInt32(specificCard[0].dp) + "\n\r";
                    ToolTipService.SetToolTip(myImage, toolTip);
                    stackPanel.Children.Add(myImage);
                }
                scrollViewer.Content = stackPanel;
            }
        }
        private void querySpecificCard(String n, String card)
        {
            requestSpecificCard = new RestRequest("/api-public/search.php?n=" + n + "&card=" + card, Method.Get);
        }

        private void addTimerMemorization()
        {
            timerMemorization.Interval = new TimeSpan(0, 0, 1);
            timerMemorization.Tick += timerMemorization_Tick;

            pbTimeMemorization.Maximum = timeMemorization;
            pbTimeMemorization.Value = timeMemorization;
            tbSecondsTimeMemorization.Text = timeMemorization.ToString();

            timerMemorization.Start();
        }
        private void timerMemorization_Tick(object sender, EventArgs e)
        {
            if (pbTimeMemorization.Value == 0)
            {
                timerMemorization.Stop();
                showSpecificGrid(gOnGame);
            }
            else
            {
                pbTimeMemorization.Value--;

                int sec = Int32.Parse(tbSecondsTimeMemorization.Text);
                sec--;
                tbSecondsTimeMemorization.Text = sec.ToString();
            }
        }

        private void addTimerGame()
        {
            timerGame.Interval = new TimeSpan(0, 3, 0);
            timerGame.Tick += timerGame_Tick;

            //pbTimeMemorization.Maximum = timerMemorization.Interval.Seconds;
            //tbSecondsTimeMemorization.Text = timerMemorization.Interval.Seconds.ToString();

            timerGame.Start();
        }
        private void timerGame_Tick(object sender, EventArgs e)
        {
            if (pbTimeMemorization.Value == 0)
            {
                timerGame.Stop();
                showSpecificGrid(gOnGame);
            }
            else
            {
                MessageBox.Show("Has conseguido X puntos", "Fin de partida", MessageBoxButton.OK, MessageBoxImage.Information);
            }
        }

        private void btnReturnOfCatalogue_Click(object sender, RoutedEventArgs e)
        {
            showSpecificGrid(gMenu);
        }

        private void btnPlay_Click(object sender, RoutedEventArgs e)
        {
            showSpecificGrid(gPreGame);
            //addTimerGame();
            addTimerMemorization();

            for (int i = 0; i < 3; i++)
            {
                randomNumbers[i] = rnd.Next(0, allCards.Count);

                n = allCards[randomNumbers[i]].name;
                card = allCards[randomNumbers[i]].cardnumber;
                querySpecificCard(n, card);
                specificCard = client.Execute<List<CardExtended>>(requestSpecificCard).Data;

                if (specificCard == null)
                {
                    i--;
                }
            }

            n = allCards[randomNumbers[0]].name;
            card = allCards[randomNumbers[0]].cardnumber;
            querySpecificCard(n, card);
            specificCard1 = client.Execute<List<CardExtended>>(requestSpecificCard).Data;

            n = allCards[randomNumbers[1]].name;
            card = allCards[randomNumbers[1]].cardnumber;
            querySpecificCard(n, card);
            specificCard2 = client.Execute<List<CardExtended>>(requestSpecificCard).Data;

            n = allCards[randomNumbers[2]].name;
            card = allCards[randomNumbers[2]].cardnumber;
            querySpecificCard(n, card);
            specificCard3 = client.Execute<List<CardExtended>>(requestSpecificCard).Data;

            BitmapImage bitmap1 = new BitmapImage();
            bitmap1.BeginInit();
            bitmap1.UriSource = new Uri(specificCard1[0].image_url);
            bitmap1.EndInit();
            imgCard1.Source = bitmap1;

            BitmapImage bitmap2 = new BitmapImage();
            bitmap2.BeginInit();
            bitmap2.UriSource = new Uri(specificCard2[0].image_url);
            bitmap2.EndInit();
            imgCard2.Source = bitmap2;

            BitmapImage bitmap3 = new BitmapImage();
            bitmap3.BeginInit();
            bitmap3.UriSource = new Uri(specificCard3[0].image_url);
            bitmap3.EndInit();
            imgCard3.Source = bitmap3;

            int random = rnd.Next(0, 2);
            assaignOptions(random);
        }

        private void assaignOptions(int random)
        {
            if(random == 0)
            {
                cbStage11.Content = specificCard1[0].stage;
                cbStage12.Content = specificCard2[0].stage;
                cbStage13.Content = specificCard3[0].stage;
                cbStage21.Content = specificCard1[0].stage;
                cbStage22.Content = specificCard2[0].stage;
                cbStage23.Content = specificCard3[0].stage;
                cbStage31.Content = specificCard1[0].stage;
                cbStage32.Content = specificCard2[0].stage;
                cbStage33.Content = specificCard3[0].stage;

                cbDType11.Content = specificCard3[0].digi_type;
                cbDType12.Content = specificCard2[0].digi_type;
                cbDType13.Content = specificCard1[0].digi_type;
                cbDType21.Content = specificCard3[0].digi_type;
                cbDType22.Content = specificCard2[0].digi_type;
                cbDType23.Content = specificCard1[0].digi_type;
                cbDType31.Content = specificCard3[0].digi_type;
                cbDType32.Content = specificCard2[0].digi_type;
                cbDType33.Content = specificCard1[0].digi_type;

                cbAttr11.Content = specificCard2[0].attribute;
                cbAttr12.Content = specificCard1[0].attribute;
                cbAttr13.Content = specificCard3[0].attribute;
                cbAttr21.Content = specificCard2[0].attribute;
                cbAttr22.Content = specificCard1[0].attribute;
                cbAttr23.Content = specificCard3[0].attribute;
                cbAttr31.Content = specificCard2[0].attribute;
                cbAttr32.Content = specificCard1[0].attribute;
                cbAttr33.Content = specificCard3[0].attribute;
            }
            else
            {
                cbStage11.Content = specificCard3[0].stage;
                cbStage12.Content = specificCard2[0].stage;
                cbStage13.Content = specificCard1[0].stage;
                cbStage21.Content = specificCard3[0].stage;
                cbStage22.Content = specificCard2[0].stage;
                cbStage23.Content = specificCard1[0].stage;
                cbStage31.Content = specificCard3[0].stage;
                cbStage32.Content = specificCard2[0].stage;
                cbStage33.Content = specificCard1[0].stage;

                cbDType11.Content = specificCard1[0].digi_type;
                cbDType12.Content = specificCard2[0].digi_type;
                cbDType13.Content = specificCard3[0].digi_type;
                cbDType21.Content = specificCard1[0].digi_type;
                cbDType22.Content = specificCard2[0].digi_type;
                cbDType23.Content = specificCard3[0].digi_type;
                cbDType31.Content = specificCard1[0].digi_type;
                cbDType32.Content = specificCard2[0].digi_type;
                cbDType33.Content = specificCard3[0].digi_type;

                cbAttr11.Content = specificCard3[0].attribute;
                cbAttr12.Content = specificCard1[0].attribute;
                cbAttr13.Content = specificCard2[0].attribute;
                cbAttr21.Content = specificCard3[0].attribute;
                cbAttr22.Content = specificCard1[0].attribute;
                cbAttr23.Content = specificCard2[0].attribute;
                cbAttr31.Content = specificCard3[0].attribute;
                cbAttr32.Content = specificCard1[0].attribute;
                cbAttr33.Content = specificCard2[0].attribute;
            }
        }

        private void slNivelCard1_ValueChanged(object sender, RoutedPropertyChangedEventArgs<double> e)
        {
            tbNivelCard1.Text = slNivelCard1.Value.ToString();
        }

        private void slEvoCard1_ValueChanged(object sender, RoutedPropertyChangedEventArgs<double> e)
        {
            tbEvoCard1.Text = slEvoCard1.Value.ToString();
        }

        private void slJuegoCard1_ValueChanged(object sender, RoutedPropertyChangedEventArgs<double> e)
        {
            tbJuegoCard1.Text = slJuegoCard1.Value.ToString();
        }

        private void slDPCard1_ValueChanged(object sender, RoutedPropertyChangedEventArgs<double> e)
        {
            tbDPCard1.Text = slDPCard1.Value.ToString();
        }

        private void slNivelCard2_ValueChanged(object sender, RoutedPropertyChangedEventArgs<double> e)
        {
            tbNivelCard2.Text = slNivelCard2.Value.ToString();
        }

        private void slEvoCard2_ValueChanged(object sender, RoutedPropertyChangedEventArgs<double> e)
        {
            tbEvoCard2.Text = slEvoCard2.Value.ToString();
        }

        private void slJuegoCard2_ValueChanged(object sender, RoutedPropertyChangedEventArgs<double> e)
        {
            tbJuegoCard2.Text = slJuegoCard2.Value.ToString();
        }

        private void slDPCard2_ValueChanged(object sender, RoutedPropertyChangedEventArgs<double> e)
        {
            tbDPCard2.Text = slDPCard2.Value.ToString();
        }

        private void slNivelCard3_ValueChanged(object sender, RoutedPropertyChangedEventArgs<double> e)
        {
            tbNivelCard3.Text = slNivelCard3.Value.ToString();
        }

        private void slEvoCard3_ValueChanged(object sender, RoutedPropertyChangedEventArgs<double> e)
        {
            tbEvoCard3.Text = slEvoCard3.Value.ToString();
        }

        private void slJuegoCard3_ValueChanged(object sender, RoutedPropertyChangedEventArgs<double> e)
        {
            tbJuegoCard3.Text = slJuegoCard3.Value.ToString();
        }

        private void slDPCard3_ValueChanged(object sender, RoutedPropertyChangedEventArgs<double> e)
        {
            tbDPCard3.Text = slDPCard3.Value.ToString();
        }

        private void slt_ValueChanged(object sender, RoutedPropertyChangedEventArgs<double> e)
        {
            //tbt.Text.Equals(slt.Value.ToString());  
        }

        private void btnAccept1_Click(object sender, RoutedEventArgs e)
        {
            btnAccept1.IsEnabled = false;
            checkButtons(btnAccept1, btnAccept2, btnAccept3);
        }

        private void btnAccept2_Click(object sender, RoutedEventArgs e)
        {
            btnAccept2.IsEnabled = false;
            checkButtons(btnAccept1, btnAccept2, btnAccept3);
        }

        private void btnAccept3_Click(object sender, RoutedEventArgs e)
        {
            btnAccept3.IsEnabled = false;
            checkButtons(btnAccept1, btnAccept2, btnAccept3);
        }

        private void checkButtons(Button btn1, Button btn2, Button btn3)
        {
            if (!btn1.IsEnabled && !btn2.IsEnabled && !btn3.IsEnabled)
            {
                checkScore();
            }
        }

        private void checkScore()
        {
            if (tboxName1.Text.Equals(specificCard1[0].name))
            {
                score++;
            }
            if (tboxName2.Text.Equals(specificCard2[0].name))
            {
                score++;
            }
            if (tboxName3.Text.Equals(specificCard3[0].name))
            {
                score++;
            }

            if (cbColor1.SelectedItem.ToString().Equals(specificCard1[0].color))
            {
                score++;
            }
            if (cbColor2.SelectedItem.Equals(specificCard2[0].color))
            {
                score++;
            }
            if (cbColor3.SelectedItem.Equals(specificCard3[0].color))
            {
                score++;
            }

            if ((bool)rbType11.IsChecked & specificCard1[0].type.Equals("Digimon"))
            {
                score++;
            }
            else if((bool)rbType12.IsChecked & specificCard1[0].type.Equals("Option"))
            {
                score++;
            }
            else if ((bool)rbType13.IsChecked & specificCard1[0].type.Equals("Tamer"))
            {
                score++;
            }
            else if ((bool)rbType14.IsChecked & specificCard1[0].type.Equals("Digi-Egg"))
            {
                score++;
            }
            else if ((bool)rbType21.IsChecked & specificCard2[0].type.Equals("Digimon"))
            {
                score++;
            }
            else if ((bool)rbType22.IsChecked & specificCard2[0].type.Equals("Option"))
            {
                score++;
            }
            else if ((bool)rbType23.IsChecked & specificCard2[0].type.Equals("Tamer"))
            {
                score++;
            }
            else if ((bool)rbType24.IsChecked & specificCard2[0].type.Equals("Digi-Egg"))
            {
                score++;
            }
            else if ((bool)rbType31.IsChecked & specificCard3[0].type.Equals("Digimon"))
            {
                score++;
            }
            else if ((bool)rbType32.IsChecked & specificCard3[0].type.Equals("Option"))
            {
                score++;
            }
            else if ((bool)rbType33.IsChecked & specificCard3[0].type.Equals("Tamer"))
            {
                score++;
            }
            else if ((bool)rbType34.IsChecked & specificCard3[0].type.Equals("Digi-Egg"))
            {
                score++;
            }

            if((bool)cbStage11.IsChecked & cbStage11.Content.Equals(specificCard1[0].stage)){
                score++;
            }
            else if((bool)cbStage11.IsChecked & !cbStage11.Content.Equals(specificCard1[0].stage))
            {
                score--;
            }
            if ((bool)cbStage12.IsChecked & cbStage12.Content.Equals(specificCard1[0].stage))
            {
                score++;
            }
            else if ((bool)cbStage12.IsChecked & !cbStage12.Content.Equals(specificCard1[0].stage))
            {
                score--;
            }
            if ((bool)cbStage13.IsChecked & cbStage13.Content.Equals(specificCard1[0].stage))
            {
                score++;
            }
            else if ((bool)cbStage13.IsChecked & !cbStage13.Content.Equals(specificCard1[0].stage))
            {
                score--;
            }

            if ((bool)cbStage21.IsChecked & cbStage21.Content.Equals(specificCard2[0].stage))
            {
                score++;
            }
            else if ((bool)cbStage21.IsChecked & !cbStage21.Content.Equals(specificCard2[0].stage))
            {
                score--;
            }
            if ((bool)cbStage22.IsChecked & cbStage22.Content.Equals(specificCard2[0].stage))
            {
                score++;
            }
            else if ((bool)cbStage22.IsChecked & !cbStage22.Content.Equals(specificCard2[0].stage))
            {
                score--;
            }
            if ((bool)cbStage23.IsChecked & cbStage23.Content.Equals(specificCard2[0].stage))
            {
                score++;
            }
            else if ((bool)cbStage23.IsChecked & !cbStage23.Content.Equals(specificCard2[0].stage))
            {
                score--;
            }

            if ((bool)cbStage31.IsChecked & cbStage31.Content.Equals(specificCard3[0].stage))
            {
                score++;
            }
            else if ((bool)cbStage31.IsChecked & !cbStage31.Content.Equals(specificCard3[0].stage))
            {
                score--;
            }
            if ((bool)cbStage32.IsChecked & cbStage32.Content.Equals(specificCard3[0].stage))
            {
                score++;
            }
            else if ((bool)cbStage32.IsChecked & !cbStage32.Content.Equals(specificCard3[0].stage))
            {
                score--;
            }
            if ((bool)cbStage33.IsChecked & cbStage33.Content.Equals(specificCard3[0].stage))
            {
                score++;
            }
            else if ((bool)cbStage33.IsChecked & !cbStage33.Content.Equals(specificCard3[0].stage))
            {
                score--;
            }

            if ((bool)cbDType11.IsChecked & cbDType11.Content.Equals(specificCard1[0].digi_type))
            {
                score++;
            }
            else if ((bool)cbDType11.IsChecked & !cbDType11.Content.Equals(specificCard1[0].digi_type))
            {
                score--;
            }
            if ((bool)cbDType12.IsChecked & cbDType12.Content.Equals(specificCard1[0].digi_type))
            {
                score++;
            }
            else if ((bool)cbDType12.IsChecked & !cbDType12.Content.Equals(specificCard1[0].digi_type))
            {
                score--;
            }
            if ((bool)cbDType13.IsChecked & cbDType13.Content.Equals(specificCard1[0].digi_type))
            {
                score++;
            }
            else if ((bool)cbDType13.IsChecked & !cbDType13.Content.Equals(specificCard1[0].digi_type))
            {
                score--;
            }

            if ((bool)cbDType21.IsChecked & cbDType21.Content.Equals(specificCard2[0].digi_type))
            {
                score++;
            }
            else if ((bool)cbDType21.IsChecked & !cbDType21.Content.Equals(specificCard2[0].digi_type))
            {
                score--;
            }
            if ((bool)cbDType22.IsChecked & cbDType22.Content.Equals(specificCard2[0].digi_type))
            {
                score++;
            }
            else if ((bool)cbDType22.IsChecked & !cbDType22.Content.Equals(specificCard2[0].digi_type))
            {
                score--;
            }
            if ((bool)cbDType23.IsChecked & cbDType23.Content.Equals(specificCard1[0].digi_type))
            {
                score++;
            }
            else if ((bool)cbDType23.IsChecked & !cbDType23.Content.Equals(specificCard1[0].digi_type))
            {
                score--;
            }

            if ((bool)cbDType31.IsChecked & cbDType31.Content.Equals(specificCard3[0].digi_type))
            {
                score++;
            }
            else if ((bool)cbDType31.IsChecked & !cbDType31.Content.Equals(specificCard3[0].digi_type))
            {
                score--;
            }
            if ((bool)cbDType32.IsChecked & cbDType32.Content.Equals(specificCard3[0].digi_type))
            {
                score++;
            }
            else if ((bool)cbDType32.IsChecked & !cbDType32.Content.Equals(specificCard3[0].digi_type))
            {
                score--;
            }
            if ((bool)cbDType33.IsChecked & cbDType33.Content.Equals(specificCard3[0].digi_type))
            {
                score++;
            }
            else if ((bool)cbDType33.IsChecked & !cbDType33.Content.Equals(specificCard3[0].digi_type))
            {
                score--;
            }

            if ((bool)cbAttr11.IsChecked & cbAttr11.Content.Equals(specificCard1[0].attribute))
            {
                score++;
            }
            else if ((bool)cbAttr11.IsChecked & !cbAttr11.Content.Equals(specificCard1[0].attribute))
            {
                score--;
            }
            if ((bool)cbAttr12.IsChecked & cbAttr12.Content.Equals(specificCard1[0].attribute))
            {
                score++;
            }
            else if ((bool)cbAttr12.IsChecked & !cbAttr12.Content.Equals(specificCard1[0].attribute))
            {
                score--;
            }
            if ((bool)cbAttr13.IsChecked & cbAttr13.Content.Equals(specificCard1[0].attribute))
            {
                score++;
            }
            else if ((bool)cbAttr13.IsChecked & !cbAttr13.Content.Equals(specificCard1[0].attribute))
            {
                score--;
            }

            if ((bool)cbAttr21.IsChecked & cbAttr21.Content.Equals(specificCard2[0].attribute))
            {
                score++;
            }
            else if ((bool)cbAttr21.IsChecked & !cbAttr21.Content.Equals(specificCard2[0].attribute))
            {
                score--;
            }
            if ((bool)cbAttr22.IsChecked & cbAttr22.Content.Equals(specificCard2[0].attribute))
            {
                score++;
            }
            else if ((bool)cbAttr22.IsChecked & !cbAttr22.Content.Equals(specificCard2[0].attribute))
            {
                score--;
            }
            if ((bool)cbAttr23.IsChecked & cbAttr23.Content.Equals(specificCard2[0].attribute))
            {
                score++;
            }
            else if ((bool)cbAttr23.IsChecked & !cbAttr23.Content.Equals(specificCard2[0].attribute))
            {
                score--;
            }

            if ((bool)cbAttr31.IsChecked & cbAttr31.Content.Equals(specificCard3[0].attribute))
            {
                score++;
            }
            else if ((bool)cbAttr31.IsChecked & !cbAttr31.Content.Equals(specificCard3[0].attribute))
            {
                score--;
            }
            if ((bool)cbAttr32.IsChecked & cbAttr32.Content.Equals(specificCard3[0].attribute))
            {
                score++;
            }
            else if ((bool)cbAttr32.IsChecked & !cbAttr32.Content.Equals(specificCard3[0].attribute))
            {
                score--;
            }
            if ((bool)cbAttr33.IsChecked & cbAttr33.Content.Equals(specificCard3[0].attribute))
            {
                score++;
            }
            else if ((bool)cbAttr33.IsChecked & !cbAttr33.Content.Equals(specificCard3[0].attribute))
            {
                score--;
            }

            if(slNivelCard1.Value == specificCard1[0].level)
            {
                score++;
            }
            if (slNivelCard2.Value == specificCard2[0].level)
            {
                score++;
            }
            if (slNivelCard3.Value == specificCard3[0].level)
            {
                score++;
            }

            if (slEvoCard1.Value == specificCard1[0].evolution_cost)
            {
                score++;
            }
            if (slEvoCard2.Value == specificCard2[0].evolution_cost)
            {
                score++;
            }
            if (slEvoCard3.Value == specificCard3[0].evolution_cost)
            {
                score++;
            }

            if (slJuegoCard1.Value == specificCard1[0].play_cost)
            {
                score++;
            }
            if (slJuegoCard2.Value == specificCard2[0].play_cost)
            {
                score++;
            }
            if (slJuegoCard3.Value == specificCard3[0].play_cost)
            {
                score++;
            }

            if (specificCard1[0].dp - 50 < slDPCard1.Value && slDPCard1.Value < specificCard1[0].dp + 50)
            {
                score++;
            }
            if (specificCard2[0].dp - 50 < slDPCard2.Value && slDPCard2.Value < specificCard2[0].dp + 50)
            {
                score++;
            }
            if (specificCard3[0].dp - 50 < slDPCard3.Value && slDPCard3.Value < specificCard3[0].dp + 50)
            {
                score++;
            }

            showEndGameMsg();
            showSpecificGrid(gMenu);
        }

        private void showEndGameMsg()
        {
            MessageBox.Show("¿Has conseguido: " + score + "/30 puntos!", "Fin de partida", MessageBoxButton.OK, MessageBoxImage.Information);
            btnAccept1.IsEnabled = true; 
            btnAccept2.IsEnabled = true; 
            btnAccept3.IsEnabled = true;
            score = 0;
            slDPCard1.Value = 0;
            slDPCard2.Value = 0;
            slDPCard3.Value = 0;
            slEvoCard1.Value = 0;
            slEvoCard2.Value = 0;
            slEvoCard3.Value = 0;
            slJuegoCard1.Value = 0;
            slJuegoCard2.Value = 0;
            slJuegoCard3.Value = 0;
            slNivelCard1.Value = 0;
            slNivelCard2.Value = 0;
            slNivelCard3.Value = 0;
            tboxName1.Text = "";
            tboxName2.Text = "";
            tboxName3.Text = "";
        }

        private void btnDifficulty_Click(object sender, RoutedEventArgs e)
        {
            //showSpecificGrid(gDifficulty);
        }
    }
}
