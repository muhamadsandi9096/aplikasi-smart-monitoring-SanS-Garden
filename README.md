# aplikasi-smart-monitoring-SanS-Garden
Project ini merupakan project penelitian ilmiah dari sistem purwarupa monitoring dan penyiraman secara otomatis maupun manual pada sebuah tanaman. Pada tanaman sendiri perlu diperhatikan beberapa faktor penting, seperti pH tanah yang ideal dan suhu optimal. Selain itu, faktor penyiraman juga memainkan peran krusial dalam budidaya tanaman. Maka dari faktor tersebut diperlukan sebuah sistem yang dapat membantu petani dalam memantau kondisi pertumbuhan tanaman secara efisien dan melakukan penyiraman yang sesuai dengan kebutuhan tanaman. Sistem yang dirancang ini menggunakan mikrokontroler ESP-32 dan dan beberapa sensor, termasuk sensor kelembaban tanah untuk mengetahui kadar air yang ada di dalam tanah, sensor pH untuk mengetahui kadar pH di dalam tanah, serta sensor suhu dan kelembaban untuk mengetahui temperatur dan kelembaban udara di sekitar tanaman. Berikutnya untuk hasil data yang diperoleh dari setiap sensor akan ditampilkan pada layar LCD dan akan dilanjutkan untuk mengaktifkan output terkait. Selain itu, data tersebut akan dikirimkan ke Firebase sebagai real-time database dan akan diteruskan dan ditampilkan pada aplikasi Android. Aplikasi tersebut tidak hanya menampilkan data dari sensor, tetapi juga memungkinkan pengguna untuk melakukan kontrol manual terhadap penyiraman tanaman jika diperlukan. Dalam mode otomatis, sistem penyiraman akan aktif setiap pukul 07.00 pagi dan akan berfungsi kembali jika sensor mendeteksi keadaan tanah yang kering. 

## Fitur Aplikasi
1. Dapat memantau (monitoring) hasil kelembaban tanah, cahaya, suhu dan kelembaban udara, kadar pH tanah, dan cuaca.
2. Dapat mengontrol secara manual penyiraman dari tanaman
3. Terdapat kumpulan referensi jurnal yang digunakan dalam penelitian
4. terdapat fitur login untuk membatasi pengguna

## Fitur Sistem Alat 
1. Dapat melakukan penyiraman secara otomatis berdasarkan kelembaban tanah dan kondisi lingkungan.
2. Dapat melakukan penyiraman secara otomatis setiap jam 07:00
3. Dapat dikontrol secara manual dari aplikasi.
4. Dapat menampilkan hasil yang didapat dari sensor ke LCD

## Komponen Penunjang Pembuatan aplikasi dan sistem alat
1. Android Studio (java)
2. Arduino IDE
3. Firebase Realtime database & authentication
4. Mikrokontroler ESP32
5. Sensor Kelembaban Tanah, cahaya, suhu dan kelembaban udara, kadar pH tanah, dan cuaca.
6. Komponen output alat seperti relay, LCD I2C (20x4), Relay, Pompa DC 12V.
