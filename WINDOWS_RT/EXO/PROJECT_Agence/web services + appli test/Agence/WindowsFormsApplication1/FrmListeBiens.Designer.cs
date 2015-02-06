namespace WindowsFormsApplication1 {
    partial class FrmListeBiens {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing) {
            if (disposing && (components != null)) {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent() {
            this.lsvAnnonces = new System.Windows.Forms.ListView();
            this.colTitre = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.colPrix = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.colVille = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.btnSupprimer = new System.Windows.Forms.Button();
            this.btnSaisir = new System.Windows.Forms.Button();
            this.cboTypeBien = new System.Windows.Forms.ComboBox();
            this.button1 = new System.Windows.Forms.Button();
            this.btnAfficher = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // lsvAnnonces
            // 
            this.lsvAnnonces.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            this.colTitre,
            this.colPrix,
            this.colVille});
            this.lsvAnnonces.FullRowSelect = true;
            this.lsvAnnonces.HideSelection = false;
            this.lsvAnnonces.Location = new System.Drawing.Point(48, 49);
            this.lsvAnnonces.MultiSelect = false;
            this.lsvAnnonces.Name = "lsvAnnonces";
            this.lsvAnnonces.Size = new System.Drawing.Size(563, 379);
            this.lsvAnnonces.Sorting = System.Windows.Forms.SortOrder.Ascending;
            this.lsvAnnonces.TabIndex = 6;
            this.lsvAnnonces.UseCompatibleStateImageBehavior = false;
            this.lsvAnnonces.View = System.Windows.Forms.View.Details;
            // 
            // colTitre
            // 
            this.colTitre.Text = "Titre";
            this.colTitre.Width = 80;
            // 
            // colPrix
            // 
            this.colPrix.Text = "Prix";
            // 
            // colVille
            // 
            this.colVille.Text = "Ville";
            this.colVille.Width = 135;
            // 
            // btnSupprimer
            // 
            this.btnSupprimer.Location = new System.Drawing.Point(653, 232);
            this.btnSupprimer.Name = "btnSupprimer";
            this.btnSupprimer.Size = new System.Drawing.Size(99, 29);
            this.btnSupprimer.TabIndex = 11;
            this.btnSupprimer.Text = "Supprimer";
            this.btnSupprimer.UseVisualStyleBackColor = true;
            this.btnSupprimer.Click += new System.EventHandler(this.btnSupprimer_Click);
            // 
            // btnSaisir
            // 
            this.btnSaisir.Location = new System.Drawing.Point(653, 180);
            this.btnSaisir.Name = "btnSaisir";
            this.btnSaisir.Size = new System.Drawing.Size(108, 34);
            this.btnSaisir.TabIndex = 10;
            this.btnSaisir.Text = "Saisir un bien";
            this.btnSaisir.UseVisualStyleBackColor = true;
            this.btnSaisir.Click += new System.EventHandler(this.btnSaisir_Click);
            // 
            // cboTypeBien
            // 
            this.cboTypeBien.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cboTypeBien.FormattingEnabled = true;
            this.cboTypeBien.Location = new System.Drawing.Point(634, 138);
            this.cboTypeBien.Name = "cboTypeBien";
            this.cboTypeBien.Size = new System.Drawing.Size(156, 21);
            this.cboTypeBien.TabIndex = 9;
            this.cboTypeBien.SelectedIndexChanged += new System.EventHandler(this.cboTypeBien_SelectedIndexChanged);
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(670, 303);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(75, 23);
            this.button1.TabIndex = 8;
            this.button1.Text = "Fermer";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // btnAfficher
            // 
            this.btnAfficher.Location = new System.Drawing.Point(653, 76);
            this.btnAfficher.Name = "btnAfficher";
            this.btnAfficher.Size = new System.Drawing.Size(109, 34);
            this.btnAfficher.TabIndex = 7;
            this.btnAfficher.Text = "Afficher les annonces";
            this.btnAfficher.UseVisualStyleBackColor = true;
            this.btnAfficher.Click += new System.EventHandler(this.btnAfficher_Click);
            // 
            // FrmListebiens
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(811, 458);
            this.Controls.Add(this.lsvAnnonces);
            this.Controls.Add(this.btnSupprimer);
            this.Controls.Add(this.btnSaisir);
            this.Controls.Add(this.cboTypeBien);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.btnAfficher);
            this.Name = "FrmListebiens";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "FrmListeAnnonces";
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.ListView lsvAnnonces;
        private System.Windows.Forms.ColumnHeader colTitre;
        private System.Windows.Forms.ColumnHeader colPrix;
        private System.Windows.Forms.ColumnHeader colVille;
        private System.Windows.Forms.Button btnSupprimer;
        private System.Windows.Forms.Button btnSaisir;
        private System.Windows.Forms.ComboBox cboTypeBien;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.Button btnAfficher;
    }
}