using System;
using System.Windows.Forms;
using AgenceDTO;
using AgenceService;
using System.Collections.Generic;

namespace WindowsFormsApplication1 {

    public partial class FrmListeBiens : Form {

        public FrmListeBiens() {
            InitializeComponent();
        }


        private void ChargerTypesBiens() {
            List<TypeBienDTO> typesBiens = TypeBienService.ChargerListesTypesBiens();
            cboTypeBien.Items.Clear();
            cboTypeBien.Items.Add(new ComboBoxItem(-1, "Toutes"));
            foreach (TypeBienDTO typeBien in typesBiens) 
                cboTypeBien.Items.Add(new ComboBoxItem(typeBien.IdTypeBien,typeBien.LibelleType));
        }


        private void ChargerBiens(int IdTypeBien = -1) {
            lsvAnnonces.Items.Clear();
            List<BienDTO> Biens = BienService.ChargerListeBiens(IdTypeBien);

            foreach (BienDTO Bien in Biens) {
                ListViewItem item = new ListViewItem();
                item.Tag = Bien.IdBien;
                item.Text = Bien.Titre;
                item.SubItems.Add(Bien.Adresse);
                lsvAnnonces.Items.Add(item);
            }
        }


        private void btnAfficher_Click(object sender, EventArgs e) {
            ChargerTypesBiens();
            ChargerBiens();
        }


        private void button1_Click(object sender, EventArgs e) {
            Close();
        }


        private void btnSaisir_Click(object sender, EventArgs e) {
            FrmNouveauBien frm = new FrmNouveauBien();
            if (frm.ShowDialog() == System.Windows.Forms.DialogResult.OK)
                ChargerBiens();
        }


        private void btnSupprimer_Click(object sender, EventArgs e) {
            if ((lsvAnnonces.SelectedItems.Count > 0) &&
                (MessageBox.Show("Voulez-vous supprimer le Bien ?", "Question", MessageBoxButtons.YesNo) == DialogResult.Yes)) {

                ListViewItem item = lsvAnnonces.SelectedItems[lsvAnnonces.SelectedItems.Count - 1];
                int idBien = Convert.ToInt16(item.Tag);

                try {
                    BienService.Supprimer(idBien);
                }
                catch (Exception ex) {
                    MessageBox.Show(ex.Message);
                }

                finally {
                    //connexion.Close();
                    //if (cboTypeBien.SelectedIndex > -1)
                    //    ChargerBiens(((ComboBoxItem)cboTypeBien.SelectedItem).ID);
                    //else
                    ChargerBiens();
                }
            }
        }

        private void cboTypeBien_SelectedIndexChanged(object sender, EventArgs e) {
            ChargerBiens(((ComboBoxItem)cboTypeBien.SelectedItem).ID);
        }
    }
}
