package com.example.cure.consultation;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cure.MapsActivity;
import com.example.cure.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {

    Context context;
    List<Contact> contactList;
    Dialog myDialog;

    public RecycleViewAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false);
        final MyViewHolder myViewHolder = new MyViewHolder(v);

        myDialog = new Dialog(context);
        myDialog.setContentView(R.layout.dialog_contact);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));






        myViewHolder.btCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView phone_num= myViewHolder.phone_num;
                phone_num.setText(contactList.get(myViewHolder.getAdapterPosition()).getPhn());
                String text=phone_num.getText().toString();
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel :"+phone_num));

            }
        });





       /* myViewHolder.btChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView mail=myViewHolder.emailsend;
                mail.setText(contactList.get(myViewHolder.getAdapterPosition()).getMail());
                String email = mail.getText().toString()
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , email);
                i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
                i.putExtra(Intent.EXTRA_TEXT   , "body of email");
                try {

                } catch (android.content.ActivityNotFoundException ex) {
                }
            }
        });*/


        myViewHolder.item_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dialog_name_tv = (TextView) myDialog.findViewById(R.id.dialog_name_id);
                TextView dialog_spec_tv = myDialog.findViewById(R.id.dialog_spec);
                TextView dialog_phone_tv = (TextView) myDialog.findViewById(R.id.dialog_phone_id);
                ImageView dialog_contact_img = (ImageView) myDialog.findViewById(R.id.dialog_img);
                TextView dialog_mail_tv=myDialog.findViewById(R.id.dialog_mail);
                Button dialog_call_btn=myDialog.findViewById(R.id.dialog_btn_call);
                Button dialog_btn_message=myDialog.findViewById(R.id.dialog_btn_messag);


                dialog_name_tv.setText(contactList.get(myViewHolder.getAdapterPosition()).getName());
                dialog_spec_tv.setText((contactList.get(myViewHolder.getAdapterPosition()).getSpec()));
                dialog_phone_tv.setText(contactList.get(myViewHolder.getAdapterPosition()).getPhn());
                dialog_mail_tv.setText(contactList.get(myViewHolder.getAdapterPosition()).getMail());
                /*dialog_contact_img.setImageResource(Integer.parseInt(contactList.get(myViewHolder.getAdapterPosition()).getPhoto()));*/
                Picasso.get().load(contactList.get(myViewHolder.getAdapterPosition()).getPhoto()).into(dialog_contact_img);


                dialog_call_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String mobileNo = dialog_phone_tv.getText().toString();
                        String uri = "tel:" + mobileNo.trim();
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse(uri));
                        context.startActivity(intent);

                    }
                });

                dialog_btn_message.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String email = dialog_mail_tv.getText().toString();
                        Intent i = new Intent(Intent.ACTION_SENDTO,
                                Uri.parse("mailto:" + email));
                        i.putExtra(Intent.EXTRA_EMAIL  , email);
                        i.putExtra(Intent.EXTRA_SUBJECT, "");
                        i.putExtra(Intent.EXTRA_TEXT   , "");
                        try {
                            context.startActivity(Intent.createChooser(i, "Send mail..."));
                        } catch (android.content.ActivityNotFoundException ex) {
                           // Toast.makeText(getClass(FrmtContact.class, "No email clients installed.",   Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                myDialog.show();
            }




        });


        /*myViewHolder.item_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dialog_name_tv= (TextView) myDialog.findViewById(R.id.dialog_name_id) ;
                TextView dialog_spec_tv=myDialog.findViewById(R.id.dialog_spec);
                TextView dialog_phone_tv= (TextView) myDialog.findViewById(R.id.dialog_phone_id) ;
                ImageView dialog_contact_img=(ImageView) myDialog.findViewById(R.id.dialog_img);

                dialog_name_tv.setText(contactList.get(myViewHolder.getAdapterPosition()).getName());
                dialog_spec_tv.setText((contactList.get(myViewHolder.getAdapterPosition()).getSpec()));
                dialog_phone_tv.setText(contactList.get(myViewHolder.getAdapterPosition()).getPhn());
                /*dialog_contact_img.setImageResource(Integer.parseInt(contactList.get(myViewHolder.getAdapterPosition()).getPhoto()));
                Picasso.get().load(contactList.get(myViewHolder.getAdapterPosition()).getPhoto()).into(dialog_contact_img);

                myDialog.show();
            }
        });*/

        return myViewHolder;
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Contact lc = contactList.get(position);
        holder.name.setText(lc.getName());
        holder.phone_num.setText(lc.getPhn());
        holder.spec.setText(lc.getSpec());

        /*holder.imageView.setImageResource(lc.getPhoto());*/
        Picasso.get().load(contactList.get(position).getPhoto()).into(holder.imageView);
        String number = ((TextView) holder.itemView.findViewById(R.id.ph_number)).getText().toString();
        holder.emailsend.setText(lc.getMail());

        holder.btCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mobileNo = lc.getPhn();
                String uri = "tel:" + mobileNo.trim();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(uri));
                context.startActivity(intent);
            }
        });

        holder.btChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = lc.getMail();
                Intent i = new Intent(Intent.ACTION_SENDTO,
                        Uri.parse("mailto:" + email));
                i.putExtra(Intent.EXTRA_EMAIL  , email);
                i.putExtra(Intent.EXTRA_SUBJECT, "");
                i.putExtra(Intent.EXTRA_TEXT   , "");
                try {
                    context.startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    //Toast.makeText(ConsultationActivity.class, "No email clients installed.",   Toast.LENGTH_SHORT).show();
                }
            }
        });


        holder.navig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MapsActivity.class);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {


        private LinearLayout item_contact;
        TextView name;
        TextView phone_num;
        TextView spec;
        TextView emailsend;
        ImageView imageView;
        ImageView btCall,btChat,navig;


        public MyViewHolder(View itemView) {
            super(itemView);

            item_contact = (LinearLayout) itemView.findViewById(R.id.contact_item_id);
            name = (TextView) itemView.findViewById(R.id.name_contact);
            phone_num = (TextView) itemView.findViewById(R.id.ph_number);
            spec = itemView.findViewById(R.id.dialog_spec);
            imageView = (ImageView) itemView.findViewById(R.id.img_contact);
            btCall=itemView.findViewById(R.id.call);
            btChat=itemView.findViewById(R.id.chatmessage);
            emailsend=itemView.findViewById(R.id.sendmail);
            navig=itemView.findViewById(R.id.navi);



        }

    }


   /*@Override
    public Filter getFilter() {
        return contactFilter;
    }
    private Filter contactFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Contact> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(contactListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Contact item : contactListFull) {
                    if (item.getSpec().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            contactList.clear();
            contactList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };*/
}
