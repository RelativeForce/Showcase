using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Finance_Handler.Windows.Data_Transfer;
using Finance_Handler.Windows.User_Display;
using Finance_Handler.Data_Storage;
using Finance_Handler.Database;

namespace Finance_Handler.Windows
{
    public partial class MonthlyAllowanceChanger : Form, Buffered
    {

        /// <summary>
        /// <see cref="Buffered"/> destination of the <see cref="Packet"/>s from this window. This cannot be <code>null</code>. 
        /// </summary>
        private Buffered dataDestination;

        /// <summary>
        /// Handles all the toolTips behaviours on this form.
        /// </summary>
        private ToolTipHandler toolTipHandler;

        public MonthlyAllowanceChanger(Buffered dataDestination, DateTime date)
        {
            // Check argumants
            if (dataDestination == null)
            {
                throw new NullReferenceException("Must be a valid Buffered window.");
            }

            // Initalise fields and components
            this.dataDestination = dataDestination;

            InitializeComponent();

            this.toolTipHandler = new ToolTipHandler();

            month.Value = date;

            month.MaxDate = System.DateTime.Today;

            updateBudget(date);

        }

        /// <summary>
        /// This is unused as this window is always a child of <see cref="Main_Form"/>
        /// </summary>
        /// <param name="data"></param>
        public void recieve(Packet data)
        {
            // Do nothing as this is a child of the Main Window
        }

        private void submit_Click(object sender, EventArgs e)
        {

            try
            {

                if (!oldAllowanceBox.Text.Equals(newAllowanceBox.Text))
                {
                    if (!(newAllowanceBox.Text.Equals(string.Empty)))
                    {
                        double newBudget = Double.Parse(newAllowanceBox.Text);

                        Row newRow = new Row();

                        newRow.addColoumn(Budget.MONTH_COLOUMN, (month.Value.Month < 10 ? "0" + month.Value.Month : "" + month.Value.Month) + "" + month.Value.Year);

                        newRow.addColoumn(Budget.AMOUNT_COLOUMN, "" + newBudget);

                        Packet data = new Packet(this, dataDestination, newRow);

                        dataDestination.recieve(data);

                        this.Dispose();
                    }
                    else
                    {

                        toolTipHandler.draw(
                        "Error",
                        "No new Allowance specified. Please enter a new allowance or 'Cancel'.",
                        newAllowanceBox
                        );

                    }
                }
                else
                {

                    toolTipHandler.draw(
                        "Error",
                        "Allowance is unchanged. Please enter a new allowance or 'Cancel'.",
                        newAllowanceBox
                        );


                }



            }
            catch (Exception ex)
            {

                toolTipHandler.draw(
                        "Error",
                        ex.Message,
                        newAllowanceBox
                        );

            }



        }

        private void cancel_Click(object sender, EventArgs e)
        {
            this.Dispose();
        }

        private void updateBudget(DateTime date)
        {

            string monthCode = (date.Month < 10 ? "0" + date.Month : "" + date.Month) + "" + date.Year;

            double currentValue = Budget.getInstance().getBudget(monthCode);

            if (currentValue.Equals(Double.NaN))
            {
                oldAllowanceBox.Text = "-";
            }
            else
            {
                oldAllowanceBox.Text = "" + currentValue;
            }

        }

        private void recommendButton_Click(object sender, EventArgs e)
        {
            //TODO: Recommend a allowance based on a saving goal or rate of spendature.
        }

        private void month_ValueChanged(object sender, EventArgs e)
        {
            updateBudget(month.Value);
        }

        private void MonthlyAllowanceChanger_FormClosed(object sender, FormClosedEventArgs e)
        {
            this.Dispose();
        }

        private void showAmountToolTip(object sender, EventArgs e)
        {
            toolTipHandler.draw("Input Allowance", "Input a new monthly allowance for the selected month.", newAllowanceBox);
        }

        private void showRecommendToolTip(object sender, EventArgs e)
        {
            toolTipHandler.draw("Recommend", "Unavailable", recommendButton);
        }
    }
}
