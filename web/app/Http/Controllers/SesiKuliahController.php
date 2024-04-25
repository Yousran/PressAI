<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Kreait\Firebase\Contract\Database;
use SimpleSoftwareIO\QrCode\Facades\QrCode;

class SesiKuliahController extends Controller
{
    protected $database;
    protected $mata_kuliah;
    protected $sesi_kuliah;
    public function __construct(Database $database)
    {
        $this->database = $database;
        $this->mata_kuliah = 'mata_kuliah';
        $this->sesi_kuliah = 'sesi_kuliah';
    }
    public function index() {
        $mata_kuliahs = $this->database->getReference($this->mata_kuliah)->getValue();
        return view('sesi_kuliah',compact('mata_kuliahs'));
        // return dd(compact('users'));
    }
    public function sesiKuliah($mata_kuliah_code){
        $sesi_kuliahs = $this->database->getReference($this->mata_kuliah . '/' . $mata_kuliah_code)->getValue();
        return view('view_sesi_kuliah',compact('sesi_kuliahs'));
    }
    public function create(Request $request)
    {
        $sesi_kuliah_code = str_shuffle(bin2hex(random_bytes(2)));
        $newData = $this->database->getReference($this->mata_kuliah . '/' . $request->code . '/sesi_kuliah' . '/' . $sesi_kuliah_code)->set([
            'kode_sesi' => $sesi_kuliah_code,
            'awal_waktu' => $request->awal_waktu,
            'akhir_waktu' => $request->akhir_waktu,
        ]);
        $newData = $this->database->getReference($this->sesi_kuliah . '/' . $sesi_kuliah_code)->set([
            'kode_sesi' => $sesi_kuliah_code,
            'mata_kuliah_code' => $request->code,
            'awal_waktu' => $request->awal_waktu,
            'akhir_waktu' => $request->akhir_waktu,
            // tambahkan semua field yang diperlukan
        ]);
    
        return redirect()->back();
    }
    function generateQr(Request $request) {
        $qrcode = QrCode::size(600)->generate($request->kode_sesi);
        return view('generateqr')->with('qrcode',$qrcode);
    }
}
