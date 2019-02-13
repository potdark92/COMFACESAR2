package com.comfacesar.comfacesar.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.comfacesar.ServiAmigo.Extra.Config;
import com.comfacesar.ServiAmigo.Extra.MySocialMediaSingleton;
import com.comfacesar.comfacesar.R;
import com.example.gestion.Gestion_movil_registro;
import com.example.gestion.Gestion_usuario;
import com.example.modelo.Movil_registro;
import com.example.modelo.Usuario;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link registrarUsuarioFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link registrarUsuarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class registrarUsuarioFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public registrarUsuarioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment registrarUsuarioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static registrarUsuarioFragment newInstance(String param1, String param2) {
        registrarUsuarioFragment fragment = new registrarUsuarioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private static View view_permanente;
    private EditText numeroIdentificacionEditText;
    private EditText nombreUsuarioEditText;
    private EditText apellidoEditText;
    private EditText nombreCuentaEditText;
    private EditText contraseñaCuentaEditText;
    private RadioButton masculinoRadioButton;
    private RadioButton femeninoRadioButton;
    private Button registrar_usuario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view_permanente = inflater.inflate(R.layout.fragment_registrar_usuario, container, false);
        numeroIdentificacionEditText = view_permanente.findViewById(R.id.numeroIdentificacionEditTextRegistrarUsuario);
        nombreUsuarioEditText = view_permanente.findViewById(R.id.nombreCuentaEditTextRegistrarUsuario);
        apellidoEditText = view_permanente.findViewById(R.id.apellidosEditTextRegistrarUsuario);
        nombreCuentaEditText = view_permanente.findViewById(R.id.nombreCuentaEditTextRegistrarUsuario);
        contraseñaCuentaEditText = view_permanente.findViewById(R.id.contraseñaCuentaEditTextRegistrarUsuario);
        registrar_usuario = view_permanente.findViewById(R.id.registrarmeButtonRegistrarUsuario);
        registrar_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Config.getImei() == null)
                {
                    Toast.makeText(view_permanente.getContext(), "Acepte los permiso primero antes de registrar un nuevo usuario.", Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    Toast.makeText(view_permanente.getContext(), Config.getImei(), Toast.LENGTH_LONG).show();
                }
                if(numeroIdentificacionEditText.getText().toString().isEmpty())
                {
                    Toast.makeText(view_permanente.getContext(), "Ingrese su numero de identificacion", Toast.LENGTH_LONG).show();
                    return;
                }
                if(nombreUsuarioEditText.getText().toString().isEmpty())
                {
                    Toast.makeText(view_permanente.getContext(), "Ingrese su nombres", Toast.LENGTH_LONG).show();
                    return;
                }
                if(apellidoEditText.getText().toString().isEmpty())
                {
                    Toast.makeText(view_permanente.getContext(), "Ingrese sus apellidos", Toast.LENGTH_LONG).show();
                    return;
                }
                if(nombreCuentaEditText.getText().toString().isEmpty())
                {
                    Toast.makeText(view_permanente.getContext(), "Ingrese el nombre de cuenta para iniciar sesion", Toast.LENGTH_LONG).show();
                    return;
                }if(contraseñaCuentaEditText.getText().toString().isEmpty())
                {
                    Toast.makeText(view_permanente.getContext(), "Ingrese la contraseña de la cuenta", Toast.LENGTH_LONG).show();
                    return;
                }
                Usuario usuario = new Usuario();
                usuario.numero_identificacion_usuario = numeroIdentificacionEditText.getText().toString();
                usuario.nombres_usuario = nombreUsuarioEditText.getText().toString();
                usuario.apellidos_usuario = apellidoEditText.getText().toString();
                usuario.fecha_nacimiento = "2000-1-1";
                usuario.telefono_usuario = "12333";
                usuario.direccion_usuario = "mnasbhd";
                usuario.sexo_usuario = 0;
                usuario.nombre_cuenta_usuario = nombreCuentaEditText.getText().toString();
                usuario.contrasena_usuario = contraseñaCuentaEditText.getText().toString();
                HashMap<String, String> hashMap = new Gestion_usuario().registrar_usuario(usuario);
                Log.d("Parametros : ", hashMap.toString());
                Response.Listener<String> stringListener = new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response : ", response);
                        int val = 0;
                        try
                        {
                            val = Integer.parseInt(response);
                            if(val > 0)
                            {
                                registrar_movil_registro(val);
                                Toast.makeText(view_permanente.getContext(),"Usuario registrado con exito", Toast.LENGTH_LONG).show();
                            }
                        }
                        catch (NumberFormatException exc)
                        {
                            Toast.makeText(view_permanente.getContext(),"Usuario no registrado", Toast.LENGTH_LONG).show();
                        }
                    }
                };
                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(view_permanente.getContext(),"Error en el servidor", Toast.LENGTH_LONG).show();
                    }
                };
                StringRequest stringRequest = com.comfacesar.ServiAmigo.Extra.MySocialMediaSingleton.volley_consulta(com.comfacesar.ServiAmigo.Extra.WebService.getUrl(),hashMap,stringListener, errorListener);
                MySocialMediaSingleton.getInstance(view_permanente.getContext()).addToRequestQueue(stringRequest);
            }
        });
        return view_permanente;
    }

    private void registrar_movil_registro(int id_registrado)
    {
        Movil_registro movil_registro = new Movil_registro();
        movil_registro.id_registrado_movil_registro = id_registrado;
        movil_registro.tipo_registro_movil_registro = 2;
        movil_registro.imei_movil_registro = Config.getImei();
        HashMap<String, String> hashMap = new Gestion_movil_registro().registrar_movil_registro(movil_registro);
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(view_permanente.getContext(),"Error en el servidor", Toast.LENGTH_LONG).show();
            }
        };
        StringRequest stringRequest = com.comfacesar.ServiAmigo.Extra.MySocialMediaSingleton.volley_consulta(com.comfacesar.ServiAmigo.Extra.WebService.getUrl(),hashMap,stringListener, errorListener);
        MySocialMediaSingleton.getInstance(view_permanente.getContext()).addToRequestQueue(stringRequest);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
