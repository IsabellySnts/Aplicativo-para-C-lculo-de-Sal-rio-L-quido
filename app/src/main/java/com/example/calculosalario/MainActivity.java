package com.example.calculosalario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText salarioBruto, dependentes;

    private Spinner planoSaude;
    private String tipoPlanos;

    private TextView valorSB, valorPS, valorVT, valorVA, valorVr, valorInss, valorIrrf, salarioLiquido;

    private Button btcalc, btlimpar;
    private RadioGroup groupVT, groupVA, groupVR;
    private Spinner spinnerPlanos;
    private  TextView valorPlano;


    Spinner planos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        planos = (Spinner) findViewById(R.id.spinner_planos);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.tipoPlanos, android.R.layout.simple_spinner_item);
        planos.setAdapter(adapter);

        salarioBruto = findViewById(R.id.salarioBruto);
        dependentes = findViewById(R.id.nDependentes);
        valorPS = findViewById(R.id.tv_plano);
        valorVT = findViewById(R.id.tv_vt);
        valorVA = findViewById(R.id.tv_va);
        valorVr = findViewById(R.id.tv_vr);
        salarioLiquido = findViewById(R.id.salario_liquido);
        btcalc = findViewById(R.id.bt_calcular);
        btlimpar = findViewById(R.id.bt_limpar);
        valorSB = findViewById(R.id.tv_salariobruto);
        groupVT = findViewById(R.id.radio_vt);
        groupVA = findViewById(R.id.radio_va);
        groupVR = findViewById(R.id.radio_vr);
        valorPlano = findViewById(R.id.tv_plano);
        valorInss = findViewById(R.id.INSS);
        valorIrrf = findViewById(R.id.IRRF);




        //validando os campos
        btcalc.setOnClickListener(v -> {
            if (salarioBruto.getText().toString().isEmpty()) {
                salarioBruto.setError("Informe o Salário Líquido");
            } else if (dependentes.getText().toString().isEmpty()) {
                dependentes.setError("Informe o numero de dependentes");
            } else {
                double sb, ps=0, vt=0, va=0, vr=0, inss, irrf, sl=0;
                sb = Double.parseDouble(salarioBruto.getText().toString());
                int dp=0;

                valorSB.setText(getString(R.string.valorBruto, sb));

                String item = planos.getSelectedItem().toString();

                switch (item){
                    case  "standard":
                        if (sb <= 3000) {

                            ps = 60;

                        } else {

                            ps = 80;
                        }
                        break;
                    case  "basico":
                        if (sb <= 3000) {

                            ps = 80;
                        } else {

                            ps = 110;
                        }
                        break;
                    case  "super":
                        if (sb <= 3000) {

                            ps = 95;
                        } else {

                            ps = 135;
                        }
                        break;
                    case "master":
                        if (sb <= 3000) {

                            ps = 135;
                        } else {

                            ps = 180;
                        }
                        break;

                }
                valorPlano.setText(getString(R.string.valorplano, ps));

                // pegando o valor selecionado no radio button
                switch (groupVT.getCheckedRadioButtonId()){
                    case R.id.btvt_sim:
                        vt = sb * 0.06;

                        break;
                    case  R.id.btvt_nao:
                        vt = 0;

                        break;
                }
                valorVT.setText(getString(R.string.valorvt, vt));


                switch (groupVR.getCheckedRadioButtonId()){
                    case R.id.btvr_sim:
                        if (sb <= 3000) {
                            vr = 2.60 * 22;
                        } else if (sb <= 5000) {
                            vr = 3.65 * 22;
                        } else {
                            vr = 6.50 * 22;
                        }
                        break;
                    case R.id.btvr_nao:
                        vr = 0;
                        break;
                }
                valorVr.setText(getString(R.string.valorvr, vr));


                switch (groupVA.getCheckedRadioButtonId()){
                    case  R.id.btva_sim:
                        if (sb <= 3000) {
                            va = 15.00;
                        } else if (sb <= 5000) {
                            va = 25.00;
                        } else {
                            va = 35.00;
                        }
                        break;
                    case R.id.btva_nao:
                        va = 0;
                        break;
                }
                valorVA.setText(getString(R.string.valorva, va));


                if (sb <= 1212.00) {
                    inss = sb * 0.075;

                } else if (sb <= 2427.35) {
                    inss = sb * 0.09 - 18.18;

                } else if (sb <= 3641.03) {
                    inss = sb * 0.12 - 91.00;

                } else if (sb <= 7087.22) {
                    inss = sb * 0.14 - 163.82;

                } else {
                    inss = 828.39;
                }
                valorInss.setText(getString(R.string.valorinss, inss));


                irrf = sb - inss - (189.59 * dp);
                if (irrf <= 1903.98) {
                    irrf = 0;
                } else if (irrf <= 2826.65) {
                    irrf = irrf * 0.075 - 142.80;
                } else if (irrf <= 3751.05) {
                    irrf = irrf * 0.15 - 354.80;
                } else if (irrf <= 4664.68) {
                    irrf = irrf * 0.225 - 636.13;
                } else {
                    irrf = irrf * 0.275 - 869.36;
                }
                valorIrrf.setText(getString(R.string.valorirrf, irrf));


                sl = sb - inss - vt - vr - va - irrf - ps;
                salarioLiquido.setText(getString(R.string.valorLiquido, sl));



            }

        });




    }

}