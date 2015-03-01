using System;
using System.Windows.Forms;
using AgenceDTO;
using AgenceService;
using System.Collections.Generic;
using AgenceUtils;


namespace WindowsFormsApplication1 {


    public partial class FrmNouveauBien : Form {


        public FrmNouveauBien() {
            InitializeComponent();
            ChargerTypesbiens();
        }

        private void ChargerTypesbiens() {
            List<TypeBienDTO> typesbiens = TypeBienService.ChargerListesTypesBiens();
            cboTypeBien.Items.Clear();
            foreach (TypeBienDTO typebien in typesbiens)
                cboTypeBien.Items.Add(new ComboBoxItem(typebien.IdTypeBien, typebien.LibelleType));
        }


        private void btnAnnuler_Click(object sender, EventArgs e) {
            DialogResult = System.Windows.Forms.DialogResult.Cancel;
        }


        private void btnCreer_Click(object sender, EventArgs e) {
            BienDTO bien = new BienDTO();

            try {
                if (cboTypeBien.SelectedIndex > -1) {
                    bien.IdTypeBien = ((ComboBoxItem)cboTypeBien.SelectedItem).ID;
                    bien.Titre = txtTitre.Text;
                    bien.Description = txtDescription.Text;
                    bien.Adresse = txtAdresse.Text;
                    BienService.Ajouter(bien);
                    DialogResult = System.Windows.Forms.DialogResult.OK;
                }
            }
            catch (Exception ex) {
                Utils.LogException(ex);
                MessageBox.Show(ex.Message);
            }
        }


    }
}
