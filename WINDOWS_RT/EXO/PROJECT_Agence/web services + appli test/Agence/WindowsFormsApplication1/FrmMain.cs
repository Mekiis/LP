using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using AgenceDTO;
using AgenceService;

namespace WindowsFormsApplication1 {
    public partial class FrmMain : Form {
        public FrmMain() {
            InitializeComponent();
        }

        private void quitterToolStripMenuItem_Click(object sender, EventArgs e) {
            Close();
        }

        private void gestionDespersonnesToolStripMenuItem_Click(object sender, EventArgs e) {
            FrmListeBiens frmpersonnes = new FrmListeBiens();
            frmpersonnes.ShowDialog();
        }

        private void button1_Click(object sender, EventArgs e) {
            //ProprietaireDTO p = new ProprietaireDTO();
            //p.Nom = "Salvador";
            //p.Prenom = "Henri";
            //p.Adresse = "France";
            //ProprietaireService.Ajouter(p);
            //ProprietaireService.Supprimer(5);

            AgendaDTO a = new AgendaDTO();
            AgentDTO ag = AgentService.Charger(4);
            a.Date = DateTime.Now;
            a.Titre = "Rendez-vous important 2";
            a.Agent = ag;
            AgendaService.Ajouter(a);

            AgendaService.ChargerListeRendezVous();

            AnnonceDTO an = new AnnonceDTO();
            BienDTO bien = BienService.Charger(2);
            an.Bien = bien;
            an.Titre = "A vendre pas cher";
            an.Prix = 1500;
            AnnonceService.Ajouter(an);

            AgentDTO agent = new AgentDTO();
            agent.Nom = "Sanier";
            agent.Prenom = "Henri";
            agent.Login = "secret";
            agent.MotDePasse = "ma date de naissance";
            AgentService.Ajouter(agent);

        }

    }
}
