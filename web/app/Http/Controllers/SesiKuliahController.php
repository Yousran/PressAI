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
    public function index($mata_kuliah_code) {
        $sesi_kuliahs = $this->database->getReference($this->mata_kuliah . '/' . $mata_kuliah_code)->getValue();
        return view('sesi_kuliah',compact('sesi_kuliahs'));
    }
    public function create(Request $request)
    {
        $sesi_kuliah_code = str_shuffle(bin2hex(random_bytes(2)));
        $mata_kuliah_name = $this->database->getReference($this->mata_kuliah . '/' . $request->code.'/mata_kuliah_name')->getValue();
        
        $newData = $this->database->getReference($this->mata_kuliah . '/' . $request->code . '/sesi_kuliah' . '/' . $sesi_kuliah_code)->set([
            'kode_sesi' => $sesi_kuliah_code,
            'awal_waktu' => $request->awal_waktu,
            'akhir_waktu' => $request->akhir_waktu,
            'tanggal_sesi' => $request->tanggal_sesi
        ]);
        $newData = $this->database->getReference($this->sesi_kuliah . '/' . $sesi_kuliah_code)->set([
            'kode_sesi' => $sesi_kuliah_code,
            'mata_kuliah_code' => $request->code,
            'mata_kuliah_name' => $mata_kuliah_name,
            'awal_waktu' => $request->awal_waktu,
            'akhir_waktu' => $request->akhir_waktu,
            'tanggal_sesi' => $request->tanggal_sesi
        ]);
    
        return redirect()->back();
    }
    function generateQr(Request $request) {
        $qrcode = QrCode::size(600)->generate($request->kode_sesi);
        return view('generateqr')->with('qrcode',$qrcode);
    }
}
