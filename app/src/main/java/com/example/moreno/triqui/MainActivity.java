package com.example.moreno.triqui;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    String[] triqui;
    Integer[] ver;
    Integer[] pos;
    ArrayList<Button> Ids;
    ArrayList<Integer[]> ganar=new ArrayList<>();
    int maquina=0;
    int gameOver=0;
    int prioridad=0;
    Button uno,dos,tres,cuatro,cinco,seis,siete,ocho,nueve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Integer[] win1 = {0, 1, 2};Integer[] win2 = {0, 3, 6};Integer[] win3 = {0, 4, 8};Integer[] win4 = {1, 4, 7};Integer[] win5 = {2, 4, 6};Integer[] win6 = {2, 5, 8};Integer[] win7 = {3, 4, 5};Integer[] win8 = {6, 7, 8};
        ganar.add(win1);ganar.add(win2);ganar.add(win3);ganar.add(win4);ganar.add(win5);ganar.add(win6);ganar.add(win7);ganar.add(win8);
        triqui = new String[9];
        Ids=new ArrayList<>();
        Ids.add(uno=findViewById(R.id.uno));Ids.add(dos=findViewById(R.id.dos));Ids.add(tres=findViewById(R.id.tres));Ids.add(cuatro=findViewById(R.id.cuatro));Ids.add(cinco=findViewById(R.id.cinco));Ids.add(seis=findViewById(R.id.seis));Ids.add(siete=findViewById(R.id.siete));Ids.add(ocho=findViewById(R.id.ocho));Ids.add(nueve=findViewById(R.id.nueve));

        crearMatriz();
    }

    private void crearMatriz(){
        for(int i=0;i<9;i++){
            triqui[i]=" ";
            Ids.get(i).setEnabled(true);
        }
        mostrarMatriz(1);
    }
    public void ingresarMatriz(View view){

        for(int i=0;i<9;i++){
            Log.d("verificar",String.valueOf(Ids.get(i).getId())+"    "+String.valueOf(view.getId()));
            if(Ids.get(i).getId()==view.getId()){
                if(triqui[i]=="X" || triqui[i]=="O"){
                    mostrarMatriz(1);
                    break;
                }else{
                    triqui[i]="X";
                }
                verificar("X");
                if(mostrarMatriz(0)==1){
                    return;
                }

                maquina=1;
                prioridad=0;
                verificar("O");
                mostrarMatriz(1);
                break;
            }
        }
    }
    private void maquinaArtificial(){
        int cantidad=0;
        int posicion=-1;
        for(int i=0;i<3;i++){
            if(ver[i]==1){
                cantidad++;
            }
            else if(ver[i]==-1){
                cantidad--;
            }
            else if(ver[i]==0){
                posicion=i;
            }
        }
        if(cantidad==2){
            triqui[pos[posicion]]="O";
        }
        else if(prioridad==0){
            prioridad=1;
            verificar("X");
            maquinaArtificial();
        }
        else{
            Random rnd=new Random(System.currentTimeMillis());
            int p=rnd.nextInt(8);
            while(triqui[p]=="X" || triqui[p]=="O"){
                p=rnd.nextInt(8);
            }
            triqui[p]="O";

        }
        maquina=0;
        verificar("O");
        mostrarMatriz(1);
    }

    private void verificar(String entrada){
        String entrada2;
        int verific=0;
        if(entrada.equals("X"))entrada2="O";
        else entrada2="X";
        Integer[] ver1=new Integer[3];
        Integer[] pos1=new Integer[3];
        ver=new Integer[3];
        pos=new Integer[3];
        for(int i=0;i<8;i++){
            for(int j=0;j<9;j++){
                if(triqui[j].equals(entrada)){
                    for(int c=0;c<3;c++){
                        if(j==ganar.get(i)[c]){
                            ver[c]=1;
                            pos[c]=j;
                        }
                    }
                }
                else if(triqui[j].equals(entrada2)){
                    for(int c=0;c<3;c++){
                        if(j==ganar.get(i)[c]){
                            ver[c]=-1;
                            pos[c]=j;
                        }
                    }
                }
                else{
                    for(int c=0;c<3;c++){
                        if(j==ganar.get(i)[c]){
                            ver[c]=0;
                            pos[c]=j;
                        }
                    }
                }
            }int cantidad=0;
            for(int z=0;z<3;z++){
                if(ver[z]==1){
                    cantidad++;
                }
                else if(ver[z]==-1){
                    cantidad--;
                }
            }
            if(cantidad==3){
                gameOver=1;
            }
            else if(cantidad==2 && verific==0){
                for(int a=0;a<3;a++){
                    ver1[a]=ver[a];
                    pos1[a]=pos[a];
                }
                verific=1;
            }
        }
        for(int a=0;a<3;a++){
            if(verific==1){
                ver[a]=ver1[a];
                pos[a]=pos1[a];
            }
        }

    }

    private int mostrarMatriz(int kawaii){
        int si=0;
        for(int i=0;i<9;i++){
            Ids.get(i).setText(triqui[i]);
            if(triqui[i]=="X" || triqui[i]=="O"){
                si++;
            }

        }
        if(si==9 || gameOver==1){
            int retornar=-4;
            if(si==9){
                Toast.makeText(MainActivity.this,"empate",Toast.LENGTH_LONG).show();
                retornar= 1;
            }
            else if(kawaii==1){
                Toast.makeText(MainActivity.this,"Has perdido",Toast.LENGTH_LONG).show();
                retornar= 0;

            }
            else if(kawaii==0){
                Toast.makeText(MainActivity.this,"Has ganado",Toast.LENGTH_LONG).show();
                retornar= 1;

            }
            Toast.makeText(MainActivity.this,"Juego terminado",Toast.LENGTH_LONG).show();
            maquina=0;
            gameOver=0;
            prioridad=0;
            for(int i=0;i<9;i++){
                Ids.get(i).setEnabled(false);
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    crearMatriz();

                }
            },2000);
            return retornar;
        }else if(maquina==1){
            maquinaArtificial();
            return -1;
        }
        return -1;
    }
}

