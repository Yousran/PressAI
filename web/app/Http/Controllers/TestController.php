<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Kreait\Firebase\Contract\Database;
use SimpleSoftwareIO\QrCode\Facades\QrCode;

class TestController extends Controller
{
    protected $database;
    protected $tablename;
    protected $mata_kuliah;
    public function __construct(Database $database)
    {
        $this->database = $database;
        $this->tablename = 'test';
        $this->mata_kuliah = 'mata_kuliah';
    }

    public function index($mata_kuliah_code){
        $mata_kuliah = $this->database->getReference($this->mata_kuliah . '/' . $mata_kuliah_code)->getValue();
        return view('test',compact('mata_kuliah'));
    }

    public function create(Request $request)
    {
        $test_code = str_shuffle(bin2hex(random_bytes(2)));
        $mata_kuliah_name = $this->database->getReference($this->mata_kuliah . '/' . $request->code.'/mata_kuliah_name')->getValue();
        
        $newData = $this->database->getReference($this->mata_kuliah . '/' . $request->code . '/test' . '/' . $test_code)->set([
            'test_code' => $test_code,
            'test_name' => $request->nama,
            'awal_waktu' => $request->awal_waktu,
            'akhir_waktu' => $request->akhir_waktu,
            'tanggal_test' => $request->tanggal_sesi
        ]);
        $newData = $this->database->getReference($this->tablename . '/' . $test_code)->set([
            'test_code' => $test_code,
            'test_name' => $request->nama,
            'mata_kuliah_code' => $request->code,
            'mata_kuliah_name' => $mata_kuliah_name,
            'awal_waktu' => $request->awal_waktu,
            'akhir_waktu' => $request->akhir_waktu,
            'tanggal_test' => $request->tanggal_sesi,
            'durasi_test' => $request->durasi
        ]);
    
        return redirect()->back();
    }
    public function generateQr($test_code) {
        $qrcode = QrCode::size(600)->generate($test_code);
        return view('generateqr')->with('qrcode',$qrcode);
    }

}
