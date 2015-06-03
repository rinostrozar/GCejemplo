package com.example.ricardoinostrozarebolledo.laberinto;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.Random;


public class Laberinto extends ActionBarActivity implements View.OnTouchListener {
    private int corx, cory; //
    private Lienzo fondo;
    int x, y;
    int x_ant, y_ant;
    private Casilla[][] casillas;
    private boolean activo = true;
    private boolean inicio = true;
    int casilla_x, casilla_y=0;
    String mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laberinto);

        RelativeLayout layout1 = (RelativeLayout) findViewById(R.id.layout1);
        fondo = new Lienzo(this);
        fondo.setOnTouchListener(this);
        layout1.addView(fondo);

        casillas = new Casilla[8][8];
        for (int f = 0; f < 8; f++) {
            for (int c = 0; c < 8; c++) {
                casillas[f][c] = new Casilla();

                Random r = new Random();
                int activo = r.nextInt(100 - 0 + 1) + 0;
                if(activo < 10) {
                    casillas[f][c].destapado = false;
                }else {
                    casillas[f][c].destapado = true;
                }
            }

            }

        //this.disponerBombas();
        //this.contarBombasPerimetro();
        getSupportActionBar().hide();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_laberinto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);


    }

    public boolean onTouch(View v, MotionEvent event) {
        corx = (int) event.getX();
        cory = (int) event.getY();


        if(inicio){
            x_ant= ((int) event.getX());
            y_ant= ((int) event.getY());
        }

        if(event.getAction()==2){

            if (casilla_x<=7 && casilla_x>=0) {
                if (event.getX() < x_ant) {
                    casilla_x = casilla_x + 1;
                    x_ant = ((int) event.getX());
                }
                if (event.getX() > x_ant) {
                    casilla_x = casilla_x - 1;
                    x_ant = ((int) event.getX());

                }

            }

//           6


        }







//       if(event.getAction()==1){
//
//       }



        inicio = false;
        //Cruzeta
        fondo.invalidate();






        return true;
    }


    class Lienzo extends View {



        public Lienzo(Context context) {
            super(context);
        }

        protected void onDraw(Canvas canvas) {



            canvas.drawRGB(0, 0, 0);
            int ancho = 0;
            if (canvas.getWidth() < canvas.getHeight())
                ancho = fondo.getWidth();
            else
                ancho = fondo.getHeight();
            int anchocua = ancho / 8;
            Paint paint = new Paint();
            paint.setTextSize(20);
            Paint paint2 = new Paint();
            paint2.setTextSize(20);
            paint2.setTypeface(Typeface.DEFAULT_BOLD);
            paint2.setARGB(255, 0, 0, 255);
            Paint paintlinea1 = new Paint();
            paintlinea1.setARGB(255, 255, 255, 255);
            int filaact = 0;

            for (int f = 0; f < 8; f++) {
                for (int c = 0; c < 8; c++) {
                    casillas[f][c].fijarxy(c * anchocua, filaact, anchocua);

                    if ( casillas[f][c].destapado == false) {
                        paint.setARGB(255, 153, 153, 153);

                    } else {

                        paint.setARGB(153, 204, 204, 204);

                    }

                    canvas.drawRect(c * anchocua, filaact, c * anchocua
                            + anchocua - 2, filaact + anchocua - 2, paint);
                    // linea blanca
                    canvas.drawLine(c * anchocua, filaact, c * anchocua
                            + anchocua, filaact, paintlinea1);
                    canvas.drawLine(c * anchocua + anchocua - 1, filaact, c
                                    * anchocua + anchocua - 1, filaact + anchocua,
                            paintlinea1);

                    if (casillas[f][c].contenido >= 1
                            && casillas[f][c].contenido <= 8
                            && casillas[f][c].destapado)
                        canvas.drawText(
                                String.valueOf(casillas[f][c].contenido), c
                                        * anchocua + (anchocua / 2) - 8,
                                filaact + anchocua / 2, paint2);

                    if (casillas[f][c].contenido == 80
                            && casillas[f][c].destapado) {
                        Paint bomba = new Paint();
                        bomba.setARGB(255, 255, 0, 0);
                        canvas.drawCircle(c * anchocua + (anchocua / 2),
                                filaact + (anchocua / 2), 8, bomba);
                    }

                }
                filaact = filaact + anchocua;
            }


            int playerx = 0;
            int playery=0;

            if(inicio ) {
                  playerx = casillas[0][0].x;
                  playery = casillas[0][0].y;
            }else{
                  playerx = casillas[casilla_x][casilla_y].x;
                  playery = casillas[casilla_x][casilla_y].y;
            }

            Paint pincel1 = new Paint();
            pincel1.setARGB(255, 192, 255, 62);
            canvas.drawCircle( playerx+(anchocua / 2),  playery+(anchocua / 2), 20, pincel1);

            pincel1.setARGB(255, 255, 0, 0);
            pincel1.setTextSize(30);
            pincel1.setTypeface(Typeface.SERIF);
            canvas.drawText("Mov: "+mensaje, playerx+(anchocua / 2),  playery+(anchocua / 2), pincel1);
            pincel1.setTypeface(Typeface.SANS_SERIF);



        }

    }
}

